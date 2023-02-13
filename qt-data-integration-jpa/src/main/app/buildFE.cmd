@echo off
echo Deleting old directory if exists
set FRONT_END=%cd%
rmdir /S /Q "%FRONT_END%\data-integration"
rmdir /S /Q "%FRONT_END%\..\resources\static"
rmdir /S /Q "%FRONT_END%\..\resources\public\index.html"

echo Creating build for production
call npm run build

echo Renaming build to auth folder
ren build data-integration

echo Moving index.html to public resource folder
copy data-integration\index.html "%FRONT_END%/../resources/public/"

echo Moving app to static resource folder of Framework module
echo move /Y data-integration\*.* "%FRONT_END%\..\resources\static\"
echo move /Y data-integration\static "%FRONT_END%\..\resources\static\"
move /Y data-integration "%FRONT_END%\..\resources\static"