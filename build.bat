@echo off


%windir%\Microsoft.NET\Framework\v4.0.30319\MSBuild.exe EveryPrice.sln /p:Configuration=Release /p:VisualStudioVersion=14.0 /p:Platform="Any CPU" /fileLogger


pause