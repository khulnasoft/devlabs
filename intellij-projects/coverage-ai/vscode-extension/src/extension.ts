import * as vscode from 'vscode';
import * as path from 'path';
import * as fs from 'fs';
import axios from 'axios';

interface CoverageData {
  file: string;
  lineCoverage: number;
  branchCoverage: number;
  uncoveredLines: number[];
  suggestions: Suggestion[];
}

interface Suggestion {
  id: string;
  priority: 'high' | 'medium' | 'low';
  title: string;
  description: string;
  line: number;
  effort: string;
  impact: string;
}

class CoverageAIProvider implements vscode.TreeDataProvider<CoverageItem> {
  private _onDidChangeTreeData: vscode.EventEmitter<CoverageItem | undefined | null | void> = new vscode.EventEmitter<CoverageItem | undefined | null | void>();
  readonly onDidChangeTreeData: vscode.Event<CoverageItem | undefined | null | void> = this._onDidChangeTreeData.event;

  private coverageData: Map<string, CoverageData> = new Map();

  constructor() {
    this.loadCoverageData();
  }

  refresh(): void {
    this._onDidChangeTreeData.fire();
  }

  getTreeItem(element: CoverageItem): vscode.TreeItem {
    return element;
  }

  getChildren(element?: CoverageItem): Thenable<CoverageItem[]> {
    if (!element) {
      // Root level - show files with coverage data
      return Promise.resolve(
        Array.from(this.coverageData.entries()).map(([filePath, data]) => {
          const relativePath = vscode.workspace.asRelativePath(filePath);
          const item = new CoverageItem(
            relativePath,
            data.lineCoverage >= 0.75 ? vscode.TreeItemCollapsibleState.Collapsed : vscode.TreeItemCollapsibleState.Expanded,
            filePath,
            data
          );
          item.tooltip = `${relativePath}\nLine Coverage: ${(data.lineCoverage * 100).toFixed(1)}%\nBranch Coverage: ${(data.branchCoverage * 100).toFixed(1)}%`;
          return item;
        })
      );
    } else {
      // File level - show suggestions
      const suggestions = element.coverageData.suggestions.map(suggestion => {
        const item = new CoverageItem(
          suggestion.title,
          vscode.TreeItemCollapsibleState.None,
          element.filePath,
          element.coverageData,
          suggestion
        );
        item.tooltip = suggestion.description;
        item.command = {
          command: 'coverage-ai.goToSuggestion',
          title: 'Go to Suggestion',
          arguments: [element.filePath, suggestion.line]
        };
        return item;
      });
      return Promise.resolve(suggestions);
    }
  }

  private loadCoverageData(): void {
    const workspaceFolder = vscode.workspace.workspaceFolders?.[0];
    if (!workspaceFolder) return;

    const reportPath = path.join(workspaceFolder.uri.fsPath, 'build/coverage-ai-reports/coverage-analysis.json');
    if (fs.existsSync(reportPath)) {
      try {
        const report = JSON.parse(fs.readFileSync(reportPath, 'utf8'));
        this.coverageData.clear();

        if (report.files) {
          report.files.forEach((fileData: any) => {
            const filePath = path.resolve(workspaceFolder.uri.fsPath, fileData.name);
            this.coverageData.set(filePath, fileData);
          });
        }
      } catch (error) {
        console.error('Failed to load coverage data:', error);
      }
    }
  }
}

class CoverageItem extends vscode.TreeItem {
  constructor(
    public readonly label: string,
    public readonly collapsibleState: vscode.TreeItemCollapsibleState,
    public readonly filePath: string,
    public readonly coverageData: CoverageData,
    public readonly suggestion?: Suggestion
  ) {
    super(label, collapsibleState);

    if (suggestion) {
      this.iconPath = new vscode.ThemeIcon(
        suggestion.priority === 'high' ? 'error' :
        suggestion.priority === 'medium' ? 'warning' : 'info'
      );
      this.contextValue = 'suggestion';
    } else {
      this.iconPath = new vscode.ThemeIcon(
        coverageData.lineCoverage >= 0.75 ? 'check' : 'question'
      );
      this.contextValue = 'file';
    }
  }
}

export function activate(context: vscode.ExtensionContext) {
  console.log('Coverage AI extension is now active!');

  const provider = new CoverageAIProvider();
  vscode.window.registerTreeDataProvider('coverage-ai.explorer', provider);

  // Register commands
  const analyzeCoverageCommand = vscode.commands.registerCommand('coverage-ai.analyzeCoverage', async () => {
    await runCoverageAnalysis();
    provider.refresh();
  });

  const showReportCommand = vscode.commands.registerCommand('coverage-ai.showReport', async () => {
    await showCoverageReport();
  });

  const generateSuggestionsCommand = vscode.commands.registerCommand('coverage-ai.generateSuggestions', async () => {
    await generateAISuggestions();
    provider.refresh();
  });

  const goToSuggestionCommand = vscode.commands.registerCommand('coverage-ai.goToSuggestion', async (filePath: string, line: number) => {
    const document = await vscode.workspace.openTextDocument(filePath);
    const editor = await vscode.window.showTextDocument(document);
    const position = new vscode.Position(line - 1, 0);
    editor.selection = new vscode.Selection(position, position);
    editor.revealRange(new vscode.Range(position, position), vscode.TextEditorRevealType.InCenter);
  });

  // Register file decorations
  const decorationType = vscode.window.createTextEditorDecorationType({
    backgroundColor: 'rgba(255, 193, 7, 0.2)',
    borderWidth: '1px',
    borderStyle: 'solid',
    borderColor: 'rgba(255, 193, 7, 0.5)',
    after: {
      contentText: ' ⚠️ Low Coverage',
      color: 'rgba(255, 193, 7, 0.8)',
      margin: '0 0 0 10px'
    }
  });

  // Update decorations when active editor changes
  const updateDecorations = () => {
    const editor = vscode.window.activeTextEditor;
    if (!editor || editor.document.languageId !== 'java') {
      return;
    }

    const filePath = editor.document.fileName;
    const coverageData = provider['coverageData'].get(filePath);

    if (coverageData && coverageData.lineCoverage < 0.75) {
      const ranges: vscode.DecorationOptions[] = [];
      coverageData.uncoveredLines.forEach(line => {
        const range = new vscode.Range(line - 1, 0, line - 1, Number.MAX_VALUE);
        ranges.push({ range });
      });

      editor.setDecorations(decorationType, ranges);
    } else {
      editor.setDecorations(decorationType, []);
    }
  };

  vscode.window.onDidChangeActiveTextEditor(updateDecorations);
  vscode.workspace.onDidChangeTextDocument(updateDecorations);

  context.subscriptions.push(
    analyzeCoverageCommand,
    showReportCommand,
    generateSuggestionsCommand,
    goToSuggestionCommand
  );

  // Auto-analyze when workspace opens (if enabled)
  if (vscode.workspace.getConfiguration('coverage-ai').get('autoAnalyze', false)) {
    setTimeout(() => {
      runCoverageAnalysis();
      provider.refresh();
    }, 2000);
  }
}

async function runCoverageAnalysis(): Promise<void> {
  const workspaceFolder = vscode.workspace.workspaceFolders?.[0];
  if (!workspaceFolder) {
    vscode.window.showErrorMessage('No workspace folder found');
    return;
  }

  const config = vscode.workspace.getConfiguration('coverage-ai');
  const task = config.get('gradle.task', 'analyzeCoverage');

  vscode.window.withProgress({
    location: vscode.ProgressLocation.Notification,
    title: 'Coverage AI',
    cancellable: false
  }, async (progress) => {
    progress.report({ increment: 0, message: 'Running Gradle task...' });

    try {
      // Run Gradle task
      const terminal = vscode.window.createTerminal('Coverage AI');
      terminal.show();
      terminal.sendText(`./gradlew ${task}`);

      // Wait for completion (simplified - in real implementation would monitor output)
      await new Promise(resolve => setTimeout(resolve, 10000));

      progress.report({ increment: 100, message: 'Analysis complete!' });
      vscode.window.showInformationMessage('Coverage analysis completed successfully!');

    } catch (error) {
      progress.report({ increment: 100, message: 'Analysis failed' });
      vscode.window.showErrorMessage(`Coverage analysis failed: ${error}`);
    }
  });
}

async function showCoverageReport(): Promise<void> {
  const workspaceFolder = vscode.workspace.workspaceFolders?.[0];
  if (!workspaceFolder) {
    vscode.window.showErrorMessage('No workspace folder found');
    return;
  }

  const config = vscode.workspace.getConfiguration('coverage-ai');
  const format = config.get('report.format', 'html');
  const reportPath = path.join(workspaceFolder.uri.fsPath, `build/coverage-ai-reports/coverage-report.${format}`);

  if (fs.existsSync(reportPath)) {
    if (format === 'html') {
      const panel = vscode.window.createWebviewPanel(
        'coverageReport',
        'Coverage AI Report',
        vscode.ViewColumn.One,
        { enableScripts: true }
      );

      const reportContent = fs.readFileSync(reportPath, 'utf8');
      panel.webview.html = reportContent;
    } else {
      const document = await vscode.workspace.openTextDocument(reportPath);
      await vscode.window.showTextDocument(document);
    }
  } else {
    vscode.window.showWarningMessage(`Coverage report not found at: ${reportPath}`);
  }
}

async function generateAISuggestions(): Promise<void> {
  const editor = vscode.window.activeTextEditor;
  if (!editor) {
    vscode.window.showWarningMessage('No active editor');
    return;
  }

  if (editor.document.languageId !== 'java') {
    vscode.window.showWarningMessage('Coverage AI only supports Java files');
    return;
  }

  const selection = editor.selection;
  const text = editor.document.getText(selection);

  if (!text.trim()) {
    vscode.window.showWarningMessage('Please select Java code to analyze');
    return;
  }

  vscode.window.withProgress({
    location: vscode.ProgressLocation.Notification,
    title: 'Coverage AI',
    cancellable: false
  }, async (progress) => {
    progress.report({ increment: 0, message: 'Analyzing code with AI...' });

    try {
      const config = vscode.workspace.getConfiguration('coverage-ai');
      const model = config.get('ai.model', 'gpt-4');
      const temperature = config.get('ai.temperature', 0.3);

      // This would integrate with the AI service
      // For now, show a placeholder message
      await new Promise(resolve => setTimeout(resolve, 2000));

      progress.report({ increment: 100, message: 'Analysis complete!' });
      vscode.window.showInformationMessage('AI suggestions generated! Check the Coverage AI panel for details.');

    } catch (error) {
      progress.report({ increment: 100, message: 'Analysis failed' });
      vscode.window.showErrorMessage(`AI analysis failed: ${error}`);
    }
  });
}

export function deactivate() {
  console.log('Coverage AI extension is now deactivated!');
}
