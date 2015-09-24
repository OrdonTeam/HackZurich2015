# HackZurich2015

Connecting watch and phone emulators step-by-step guide:

**Step #1**

Download Android Wear application from: 

`https://github.com/OrdonTeam/Files/raw/master/com.google.android.wearable.app-2.apk.`

Install Android Wear application using:

`adb -s emulator-555X install com.google.android.wearable.app-2.apk`

where `555X` is Phone Emulator's port (You can find this by looking at the Window-Title of the emulator) 

**Step #2**

Open the Android Wear App on the Phone Emulator. Accept the TOS and so on.

**Step 3**

Open your command prompt once again and start a telnet session on the port of your smartphone emulator:

`telnet localhost 555X`

Afterwards it'll try to connect and if it succeeds it'll show a new window saying something like this:

Android Console: type 'help' for a list of commands
OK

Now enter the following command:

`redir add tcp:5601:5601`

Afterwards it should say OK.

**Step 7**

Open the Android Wear application once again and click on the watch-icon in the ActionBar 
and if everything worked it should connect to your Wear-Emulator.
