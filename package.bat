SET mypath=%~dp0
:: echo %mypath:~0,-1%
CD /d %mypath:~0,-1%
ECHO off
FOR /D %%G IN ("*") DO (
    echo %mypath%%%G
    echo %%G | findstr /r "[.]." >nul 2>&1
        if errorlevel 1 (
            CD /d %mypath%%%G
            mvn clean package
            )
    )