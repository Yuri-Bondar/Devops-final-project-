pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
        timeout(time: 2, unit: 'MINUTES') // Hard stop for the whole pipeline
    }

    environment {
        SRC        = "${WORKSPACE}\\adamliadadiramityuri\\index.jsp"
        DEPLOY_DIR = "C:\\deploy\\devops"
        HEALTH_URL = "http://localhost:8080/Devops-final-project-/adamliadadiramityuri/"
    }

    stages {
        // Remove manual checkout - Declarative already does "Checkout SCM" automatically

        stage('Prepare Deploy Directory') {
            options { timeout(time: 20, unit: 'SECONDS') }
            steps {
                bat '''
                @echo off
                echo Preparing deploy directory...
                echo DEPLOY_DIR=%DEPLOY_DIR%

                if not exist "%DEPLOY_DIR%" (
                    echo Creating directory...
                    mkdir "%DEPLOY_DIR%"
                    if errorlevel 1 (
                        echo ERROR: mkdir failed
                        exit /b 1
                    )
                ) else (
                    echo Directory already exists.
                )

                echo Done.
                '''
            }
        }

        stage('Deploy JSP (Safe Copy)') {
            options { timeout(time: 20, unit: 'SECONDS') }
            steps {
                bat '''
                @echo off
                echo Deploying JSP...
                echo SRC=%SRC%
                echo DEPLOY_DIR=%DEPLOY_DIR%

                if not exist "%SRC%" (
                    echo ERROR: Source JSP not found: %SRC%
                    exit /b 1
                )

                copy /Y "%SRC%" "%DEPLOY_DIR%\\index.jsp"
                set COPY_EXIT=%ERRORLEVEL%
                echo COPY_EXIT_CODE=%COPY_EXIT%
                if not "%COPY_EXIT%"=="0" exit /b %COPY_EXIT%

                echo Deploy completed.
                '''
            }
        }

        stage('Monitor Check') {
            options { timeout(time: 30, unit: 'SECONDS') }
            steps {
                bat '''
                @echo off
                echo Checking %HEALTH_URL%

                powershell -NoProfile -Command ^
                "try { ^
                    $r = Invoke-WebRequest -UseBasicParsing -TimeoutSec 10 -Uri '%HEALTH_URL%' -ErrorAction Stop; ^
                    Write-Host ('STATUS CODE: ' + $r.StatusCode); ^
                    if ($r.StatusCode -ge 200 -and $r.StatusCode -lt 400) { exit 0 } else { exit 1 } ^
                 } catch { ^
                    Write-Host ('ERROR: ' + $_.Exception.Message); ^
                    exit 1 ^
                 }"
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished (success or failure).'
        }
    }
}
