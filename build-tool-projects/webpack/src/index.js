// Webpack Build Tools Entry Point
const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

console.log('Webpack Build Tools starting...');

class WebpackBuildTools {
    constructor() {
        this.projectRoot = process.cwd();
        this.distDir = path.join(this.projectRoot, 'dist');
        this.srcDir = path.join(this.projectRoot, 'src');
    }

    buildProject() {
        console.log('Building project with Webpack...');
        try {
            // Ensure dist directory exists
            if (!fs.existsSync(this.distDir)) {
                fs.mkdirSync(this.distDir, { recursive: true });
            }

            // Run webpack build
            execSync('webpack --mode=production', { stdio: 'inherit' });
            console.log('✅ Webpack build completed successfully');

            // Generate build report
            this.generateBuildReport();
        } catch (error) {
            console.error('❌ Webpack build failed:', error.message);
            process.exit(1);
        }
    }

    watchProject() {
        console.log('Watching project for changes...');
        try {
            // Run webpack dev server
            execSync('webpack serve --mode=development --open', {
                stdio: 'inherit',
                detached: false
            });
        } catch (error) {
            if (error.status !== null) {
                console.error('❌ Webpack dev server failed:', error.message);
                process.exit(1);
            }
            // Server was terminated normally
        }
    }

    runTests() {
        console.log('Running tests...');
        try {
            // Check if testing framework is configured
            if (fs.existsSync(path.join(this.projectRoot, 'jest.config.js')) ||
                fs.existsSync(path.join(this.projectRoot, 'karma.conf.js'))) {
                execSync('npm test', { stdio: 'inherit' });
            } else {
                console.log('⚠️  No test configuration found. Run "npm test" to see available options.');
            }
        } catch (error) {
            console.error('❌ Tests failed:', error.message);
            process.exit(1);
        }
    }

    cleanProject() {
        console.log('Cleaning build artifacts...');
        try {
            if (fs.existsSync(this.distDir)) {
                fs.rmSync(this.distDir, { recursive: true, force: true });
                console.log('✅ Cleaned dist directory');
            }

            // Clean other common build artifacts
            const nodeModules = path.join(this.projectRoot, 'node_modules');
            if (fs.existsSync(nodeModules)) {
                console.log('ℹ️  To clean node_modules, run: rm -rf node_modules');
            }
        } catch (error) {
            console.error('❌ Clean failed:', error.message);
            process.exit(1);
        }
    }

    installDependencies() {
        console.log('Installing dependencies...');
        try {
            execSync('npm install', { stdio: 'inherit' });
            console.log('✅ Dependencies installed successfully');
        } catch (error) {
            console.error('❌ Dependency installation failed:', error.message);
            process.exit(1);
        }
    }

    analyzeBundle() {
        console.log('Analyzing bundle...');
        try {
            // Check if webpack-bundle-analyzer is installed
            if (this.hasDependency('webpack-bundle-analyzer')) {
                execSync('webpack --mode=production --analyze', { stdio: 'inherit' });
            } else {
                console.log('⚠️  webpack-bundle-analyzer not installed. Install with: npm install --save-dev webpack-bundle-analyzer');
            }
        } catch (error) {
            console.error('❌ Bundle analysis failed:', error.message);
            process.exit(1);
        }
    }

    lintCode() {
        console.log('Linting code...');
        try {
            // Check if ESLint is configured
            if (fs.existsSync(path.join(this.projectRoot, '.eslintrc.js')) ||
                fs.existsSync(path.join(this.projectRoot, '.eslintrc.json'))) {
                execSync('npx eslint src --ext .js,.jsx,.ts,.tsx', { stdio: 'inherit' });
                console.log('✅ Code linting completed');
            } else {
                console.log('⚠️  ESLint not configured. Run "npm install --save-dev eslint" to set up linting.');
            }
        } catch (error) {
            console.error('❌ Code linting failed:', error.message);
            process.exit(1);
        }
    }

    formatCode() {
        console.log('Formatting code...');
        try {
            // Check if Prettier is configured
            if (fs.existsSync(path.join(this.projectRoot, '.prettierrc')) ||
                fs.existsSync(path.join(this.projectRoot, '.prettierrc.json'))) {
                execSync('npx prettier --write src/**/*.{js,jsx,ts,tsx,css,scss}', { stdio: 'inherit' });
                console.log('✅ Code formatting completed');
            } else {
                console.log('⚠️  Prettier not configured. Run "npm install --save-dev prettier" to set up formatting.');
            }
        } catch (error) {
            console.error('❌ Code formatting failed:', error.message);
            process.exit(1);
        }
    }

    generateBuildReport() {
        try {
            const reportPath = path.join(this.distDir, 'build-report.json');
            const stats = fs.statSync(path.join(this.distDir, 'bundle.js'));

            const report = {
                timestamp: new Date().toISOString(),
                buildSize: stats.size,
                buildSizeKB: Math.round(stats.size / 1024),
                files: this.getBuildFilesInfo()
            };

            fs.writeFileSync(reportPath, JSON.stringify(report, null, 2));
            console.log('📊 Build report generated:', reportPath);
        } catch (error) {
            console.warn('⚠️  Could not generate build report:', error.message);
        }
    }

    getBuildFilesInfo() {
        try {
            return fs.readdirSync(this.distDir)
                .filter(file => fs.statSync(path.join(this.distDir, file)).isFile())
                .map(file => {
                    const filePath = path.join(this.distDir, file);
                    const stats = fs.statSync(filePath);
                    return {
                        name: file,
                        size: stats.size,
                        sizeKB: Math.round(stats.size / 1024)
                    };
                });
        } catch (error) {
            return [];
        }
    }

    hasDependency(packageName) {
        try {
            const packageJson = JSON.parse(fs.readFileSync('package.json', 'utf8'));
            return packageJson.dependencies?.[packageName] || packageJson.devDependencies?.[packageName];
        } catch (error) {
            return false;
        }
    }

    showHelp() {
        console.log(`
Webpack Build Tools - Available commands:

  build       - Build the project for production
  dev         - Start development server with hot reload
  test        - Run tests
  clean       - Remove build artifacts
  install     - Install npm dependencies
  analyze     - Analyze bundle size and dependencies
  lint        - Lint code for style and errors
  format      - Format code with Prettier
  report      - Generate build report

Examples:
  node index.js build
  node index.js dev
  node index.js test
        `);
    }
}

// Command line interface
const buildTools = new WebpackBuildTools();

function main() {
    const args = process.argv.slice(2);
    const command = args[0];

    if (!command) {
        buildTools.showHelp();
        return;
    }

    switch (command) {
        case 'build':
            buildTools.buildProject();
            break;
        case 'dev':
        case 'watch':
            buildTools.watchProject();
            break;
        case 'test':
            buildTools.runTests();
            break;
        case 'clean':
            buildTools.cleanProject();
            break;
        case 'install':
            buildTools.installDependencies();
            break;
        case 'analyze':
            buildTools.analyzeBundle();
            break;
        case 'lint':
            buildTools.lintCode();
            break;
        case 'format':
            buildTools.formatCode();
            break;
        case 'report':
            buildTools.generateBuildReport();
            break;
        case 'help':
        case '--help':
        case '-h':
            buildTools.showHelp();
            break;
        default:
            console.error(`❌ Unknown command: ${command}`);
            buildTools.showHelp();
            process.exit(1);
    }
}

// Export functions for external use
module.exports = {
    buildProject: () => buildTools.buildProject(),
    watchProject: () => buildTools.watchProject(),
    runTests: () => buildTools.runTests(),
    cleanProject: () => buildTools.cleanProject(),
    installDependencies: () => buildTools.installDependencies(),
    analyzeBundle: () => buildTools.analyzeBundle(),
    lintCode: () => buildTools.lintCode(),
    formatCode: () => buildTools.formatCode(),
    generateBuildReport: () => buildTools.generateBuildReport()
};

// Run CLI if this file is executed directly
if (require.main === module) {
    main();
}
