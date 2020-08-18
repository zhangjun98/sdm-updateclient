call chcp 65001
@echo off
set  setuplocal=%cd%
 java -Dfile.encoding=utf-8 -jar %cd%\sdm-upgradetools.jar -setuplocal %setuplocal%
pause
