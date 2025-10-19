package com.khulnasoft.spock.ai

import groovy.util.logging.Slf4j
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.*

/**
 * AST-based source code analyzer for intelligent test generation
 * Uses Groovy's AST parser for comprehensive code understanding
 */
@Slf4j
class AstSourceAnalyzer {

    /**
     * Analyzes source code using AST parsing for comprehensive understanding
     */
    SourceCodeAnalysis analyzeSourceCode(String sourceCode) {
        log.info("Performing AST-based analysis of source code")

        def analysis = new SourceCodeAnalysis()

        try {
            // Create compilation unit for AST parsing
            def configuration = new CompilerConfiguration()
            configuration.sourceEncoding = 'UTF-8'

            def sourceUnit = SourceUnit.create('DynamicSource', sourceCode, configuration)

            // Parse and build AST
            def ast = new org.codehaus.groovy.ast.builder.AstBuilder().buildFromString(
                CompilePhase.CONVERSION, sourceCode)

            if (ast && !ast.isEmpty()) {
                def moduleNode = ast[0] as ModuleNode

                // Analyze class structure
                analyzeClassStructure(moduleNode, analysis)

                // Analyze method signatures and implementations
                analyzeMethods(moduleNode, analysis)

                // Analyze field declarations
                analyzeFields(moduleNode, analysis)

                // Analyze annotations and imports
                analyzeAnnotationsAndImports(moduleNode, analysis)

                log.info("AST analysis completed successfully")
            } else {
                log.warn("No AST nodes generated from source code")
                // Fall back to regex-based analysis
                fallbackAnalysis(sourceCode, analysis)
            }

        } catch (Exception e) {
            log.error("AST parsing failed: ${e.message}", e)
            // Fall back to regex-based analysis
            fallbackAnalysis(sourceCode, analysis)
        }

        return analysis
    }

    private void analyzeClassStructure(ModuleNode moduleNode, SourceCodeAnalysis analysis) {
        moduleNode.classes.each { classNode ->
            analysis.className = classNode.name
            analysis.packageName = classNode.packageName ?: ''
            analysis.isAbstract = classNode.abstract
            analysis.superClass = classNode.superClass?.name ?: 'Object'

            // Extract implemented interfaces
            analysis.interfaces = classNode.interfaces*.name

            // Analyze class modifiers
            if (classNode.modifiers & ClassNode.ACC_PUBLIC) analysis.visibility = 'public'
            if (classNode.modifiers & ClassNode.ACC_FINAL) analysis.isFinal = true
        }
    }

    private void analyzeMethods(ModuleNode moduleNode, SourceCodeAnalysis analysis) {
        moduleNode.classes.each { classNode ->
            classNode.methods.each { methodNode ->
                def methodInfo = new MethodInfo(
                    name: methodNode.name,
                    returnType: methodNode.returnType?.name ?: 'Object',
                    parameters: extractParameterInfo(methodNode),
                    modifiers: extractMethodModifiers(methodNode),
                    exceptions: methodNode.exceptions*.name,
                    isStatic: (methodNode.modifiers & ClassNode.ACC_STATIC) != 0,
                    isPublic: (methodNode.modifiers & ClassNode.ACC_PUBLIC) != 0
                )

                // Analyze method body if available
                if (methodNode.code) {
                    methodInfo.complexity = calculateCyclomaticComplexity(methodNode.code)
                    methodInfo.linesOfCode = estimateLinesOfCode(methodNode.code)
                }

                analysis.methods << methodInfo
            }
        }
    }

    private void analyzeFields(ModuleNode moduleNode, SourceCodeAnalysis analysis) {
        moduleNode.classes.each { classNode ->
            classNode.fields.each { fieldNode ->
                def fieldInfo = new FieldInfo(
                    name: fieldNode.name,
                    type: fieldNode.type?.name ?: 'Object',
                    isStatic: (fieldNode.modifiers & ClassNode.ACC_STATIC) != 0,
                    isFinal: (fieldNode.modifiers & ClassNode.ACC_FINAL) != 0,
                    visibility: extractFieldVisibility(fieldNode.modifiers)
                )

                // Check for initialization
                if (fieldNode.initialExpression) {
                    fieldInfo.hasInitializer = true
                    fieldInfo.initializerType = fieldNode.initialExpression.class.simpleName
                }

                analysis.fields << fieldInfo
            }
        }
    }

    private void analyzeAnnotationsAndImports(ModuleNode moduleNode, SourceCodeAnalysis analysis) {
        moduleNode.classes.each { classNode ->
            // Extract class-level annotations
            analysis.annotations = classNode.annotations.collect { annotationNode ->
                [
                    name: annotationNode.classNode.name,
                    values: extractAnnotationValues(annotationNode)
                ]
            }

            // Extract imports (from the module level)
            analysis.imports = moduleNode.imports.collect { importNode ->
                [
                    className: importNode.className,
                    alias: importNode.alias,
                    isStatic: importNode.isStatic,
                    isStar: importNode.isStar
                ]
            }
        }
    }

    private List extractParameterInfo(MethodNode methodNode) {
        methodNode.parameters.collect { parameter ->
            [
                name: parameter.name,
                type: parameter.type?.name ?: 'Object',
                hasDefaultValue: parameter.hasInitialExpression(),
                defaultValue: parameter.initialExpression?.text
            ]
        }
    }

    private String extractMethodModifiers(MethodNode methodNode) {
        def modifiers = []

        if ((methodNode.modifiers & ClassNode.ACC_PUBLIC) != 0) modifiers << 'public'
        if ((methodNode.modifiers & ClassNode.ACC_PRIVATE) != 0) modifiers << 'private'
        if ((methodNode.modifiers & ClassNode.ACC_PROTECTED) != 0) modifiers << 'protected'
        if ((methodNode.modifiers & ClassNode.ACC_STATIC) != 0) modifiers << 'static'
        if ((methodNode.modifiers & ClassNode.ACC_FINAL) != 0) modifiers << 'final'
        if ((methodNode.modifiers & ClassNode.ACC_ABSTRACT) != 0) modifiers << 'abstract'

        return modifiers.join(' ')
    }

    private String extractFieldVisibility(int modifiers) {
        if ((modifiers & ClassNode.ACC_PUBLIC) != 0) return 'public'
        if ((modifiers & ClassNode.ACC_PRIVATE) != 0) return 'private'
        if ((modifiers & ClassNode.ACC_PROTECTED) != 0) return 'protected'
        return 'package-private'
    }

    private Map extractAnnotationValues(AnnotationNode annotationNode) {
        def values = [:]

        annotationNode.members.each { name, value ->
            values[name] = value?.text ?: 'true'
        }

        return values
    }

    private int calculateCyclomaticComplexity(BlockStatement blockStatement) {
        int complexity = 1 // Base complexity

        if (blockStatement) {
            blockStatement.statements.each { statement ->
                complexity += countDecisionPoints(statement)
            }
        }

        return complexity
    }

    private int countDecisionPoints(ASTNode node) {
        int decisions = 0

        // Count if-else statements
        if (node instanceof IfStatement) {
            decisions += 1
            decisions += countDecisionPoints(node.ifBlock)
            decisions += countDecisionPoints(node.elseBlock)
        }
        // Count loops
        else if (node instanceof WhileStatement || node instanceof ForStatement || node instanceof DoWhileStatement) {
            decisions += 1
            if (node instanceof WhileStatement && node.loopBlock) {
                decisions += countDecisionPoints(node.loopBlock)
            }
            if (node instanceof ForStatement && node.loopBlock) {
                decisions += countDecisionPoints(node.loopBlock)
            }
        }
        // Count switch statements
        else if (node instanceof SwitchStatement) {
            decisions += node.caseStatements.size()
        }
        // Count catch blocks
        else if (node instanceof TryCatchStatement) {
            decisions += node.catchStatements.size()
        }
        // Count conditional expressions
        else if (node instanceof TernaryExpression) {
            decisions += 1
        }
        // Count logical operators
        else if (node instanceof BinaryExpression) {
            def op = node.operation.text
            if (op in ['&&', '||', '?:']) {
                decisions += 1
            }
        }

        // Recursively check nested blocks
        if (node instanceof BlockStatement) {
            node.statements.each { stmt ->
                decisions += countDecisionPoints(stmt)
            }
        }

        return decisions
    }

    private int estimateLinesOfCode(BlockStatement blockStatement) {
        if (!blockStatement) return 0

        // Rough estimation based on AST nodes
        int lines = blockStatement.statements.size()

        blockStatement.statements.each { statement ->
            lines += estimateStatementLines(statement)
        }

        return Math.max(lines, 1)
    }

    private int estimateStatementLines(ASTNode node) {
        int lines = 0

        if (node instanceof BlockStatement) {
            lines += node.statements.size()
        } else if (node instanceof IfStatement) {
            lines += 1 // if statement
            lines += estimateStatementLines(node.ifBlock)
            if (node.elseBlock) lines += estimateStatementLines(node.elseBlock)
        } else if (node instanceof TryCatchStatement) {
            lines += 1 // try
            lines += estimateStatementLines(node.tryStatement)
            node.catchStatements.each { catchStmt ->
                lines += 1 // catch
                lines += estimateStatementLines(catchStmt.code)
            }
        } else {
            lines += 1 // Simple statement
        }

        return lines
    }

    private void fallbackAnalysis(String sourceCode, SourceCodeAnalysis analysis) {
        log.info("Using regex-based fallback analysis")

        // Extract class information
        def classMatch = sourceCode =~ /class\s+(\w+)/
        if (classMatch.find()) {
            analysis.className = classMatch.group(1)
        }

        // Extract method signatures
        def methodMatches = sourceCode =~ /def\s+(\w+)\s*\(([^)]*)\)/
        analysis.methods = methodMatches.collect { [name: it[1], parameters: it[2]] }

        // Extract field information
        def fieldMatches = sourceCode =~ /(?:def|@.*)\s+(\w+)\s*(?:=|$)/
        analysis.fields = fieldMatches.collect { [name: it[1]] }

        // Extract annotations
        def annotationMatches = sourceCode =~ /@(\w+)/
        analysis.annotations = annotationMatches.collect { [name: it[1]] }

        // Extract imports
        def importMatches = sourceCode =~ /import\s+([^;]+)/
        analysis.imports = importMatches.collect { [className: it[1]] }
    }
}

/**
 * Comprehensive source code analysis result
 */
@Slf4j
class SourceCodeAnalysis {
    String className
    String packageName
    String superClass
    List<String> interfaces = []
    boolean isAbstract = false
    boolean isFinal = false
    String visibility = 'package-private'

    List<MethodInfo> methods = []
    List<FieldInfo> fields = []
    List<Map> annotations = []
    List<Map> imports = []

    String toString() {
        "SourceCodeAnalysis{className='$className', methods=${methods.size()}, fields=${fields.size()}}"
    }

    Map toMap() {
        [
            className: className,
            packageName: packageName,
            superClass: superClass,
            interfaces: interfaces,
            isAbstract: isAbstract,
            isFinal: isFinal,
            visibility: visibility,
            methods: methods.collect { it.toMap() },
            fields: fields.collect { it.toMap() },
            annotations: annotations,
            imports: imports
        ]
    }
}

/**
 * Method information with detailed analysis
 */
@Slf4j
class MethodInfo {
    String name
    String returnType
    List<Map> parameters
    String modifiers
    List<String> exceptions = []
    boolean isStatic = false
    boolean isPublic = false
    int complexity = 1
    int linesOfCode = 0

    Map toMap() {
        [
            name: name,
            returnType: returnType,
            parameters: parameters,
            modifiers: modifiers,
            exceptions: exceptions,
            isStatic: isStatic,
            isPublic: isPublic,
            complexity: complexity,
            linesOfCode: linesOfCode
        ]
    }
}

/**
 * Field information with type and modifier details
 */
@Slf4j
class FieldInfo {
    String name
    String type
    boolean isStatic = false
    boolean isFinal = false
    String visibility
    boolean hasInitializer = false
    String initializerType

    Map toMap() {
        [
            name: name,
            type: type,
            isStatic: isStatic,
            isFinal: isFinal,
            visibility: visibility,
            hasInitializer: hasInitializer,
            initializerType: initializerType
        ]
    }
}
