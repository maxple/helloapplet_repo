rem ��� 䠩� ᮧ���� ������� � ������ ��娢� �����, � ���ன �� ��室����

@Echo Off

rem ������⢮ �஢��� ���������� �����, ��� ��室���� ᦨ������ �����, ����� ����室��� 㪠���� � ����� ��娢�
set levels=3

rem ���� � �����쭮� ����� ��娢��
set localBackupDir=..\..\Backup

rem ���� � ����筮� ����� ��娢��
set cloudBackupDir=%USERPROFILE%\Google ���\Projects

rem ��஫� ��� 䠩�� ��娢�, ����頥���� � ������
set cloudPwd=1ert2vbn

set backupFile=%localBackupDir%\bkp

set posStart=0
set posFinish=0
set counter=0

:loop

	set dir=%cd%

	:loop2

		set /a posStart-=1

		call set char=%%dir:~%posStart%,1%%

		if /i "%char%" NEQ "\" goto loop2


	set /a posStart+=1

	if /i %counter% EQU 0 call set dir=%%dir:~%posStart%%%
	if /i %counter% GTR 0 call set dir=%%dir:~%posStart%,%posFinish%%%

	set backupFile=%backupFile%_%dir%

	set /a posStart-=1

	set posFinish=%posStart%
	
	set /a counter+=1

	if /i %counter% LSS %levels% goto loop

rem set t=%time%

rem set s1=:
rem set o1=_
rem set s2= 
rem set o2=

rem set pos=0

rem :loop3

	rem set /a pos-=1

	rem call set char=%%t:~%pos%,1%%

	rem if /i "%char%" NEQ "," if /i "%char%" NEQ "." goto loop3

rem call set t=%%t:~0,%pos%%%

rem call set t=%%t:%s1%=%o1%%%
rem call set t=%%t:%s2%=%o2%%%

set localBackupFile=%backupFile%_%date%.7z
set cloudBackupFile=%backupFile%_%date%_scd.7z

"C:\Program Files\7-Zip\7z.exe" a -ssw "%localBackupFile%" "%cd%"

"C:\Program Files\7-Zip\7z.exe" a -ssw "%cloudBackupFile%" -p%cloudPwd% -mhe "%cd%"

move "%cloudBackupFile%" "%cloudBackupDir%"

pause
