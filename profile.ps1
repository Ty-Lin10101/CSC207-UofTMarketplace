param([ValidateSet('test', 'run', 'build')][string]$Task = 'test')
$ErrorActionPreference = 'Stop'
$buildPath = Join-Path $PSScriptRoot 'build/profile'
New-Item -ItemType Directory -Force -Path $buildPath | Out-Null
$sourceFiles = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src/profile') -Filter '*.java' -Recurse | ForEach-Object FullName)
$testFiles = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'test/profile') -Filter '*.java' -Recurse | ForEach-Object FullName)
& javac --release 11 -encoding UTF-8 -d $buildPath @sourceFiles @testFiles
if ($LASTEXITCODE -ne 0) { throw 'Profile compilation failed.' }
if ($Task -eq 'test') {
    & java '-Djava.awt.headless=true' -cp $buildPath profile.ProfileTests
    if ($LASTEXITCODE -ne 0) { throw 'Profile tests failed.' }
}
if ($Task -eq 'run') { & java -cp $buildPath profile.app.ProfileApp }
