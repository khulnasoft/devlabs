println 'Testing basic pipeline creation...'
try {
    def pipeline = new com.khulnasoft.spock.ai.NaturalLanguageTestPipeline()
    println '✓ Pipeline created successfully'

    def simpleReq = '''Given I have a number
When I check if it is even
Then I get the correct result'''

    def simpleCode = '''class TestClass {
    def checkEven(int n) {
        return n % 2 == 0
    }
}'''

    def result = pipeline.generateTestSpec(simpleReq, simpleCode)
    println '✓ Test generation successful'
    println 'Generated ' + result.split('\n').size() + ' lines of test code'
    println 'First 200 chars: ' + result.substring(0, Math.min(200, result.length()))

    // Save to file
    def outputDir = new File('build/generated-tests')
    outputDir.mkdirs()
    def outputFile = new File(outputDir, 'SimpleTestSpec.groovy')
    outputFile.text = result
    println '✓ Saved to: ' + outputFile.absolutePath

} catch (Exception e) {
    println '✗ Error: ' + e.message
    e.printStackTrace()
}
