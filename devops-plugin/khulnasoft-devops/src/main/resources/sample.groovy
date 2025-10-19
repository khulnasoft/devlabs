//send job metadata and junit reports with page size set to 50 (each event contains max 50 test cases)
khulnasoftins.sendTestReport(50)
//send coverage, each event contains max 50 class metrics
khulnasoftins.sendCoverageReport(50)
//send all logs from workspace to khulnasoft, with each file size limits to 10MB
khulnasoftins.archive("**/*.log", null, false, "10MB")

//end