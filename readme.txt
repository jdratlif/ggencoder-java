-------------------------------------------------------------------------------
| Java GGEncoder 1.1
| Copyright (C) 2004 emuWorks
| http://games.technoplaza.net
| Author: John David Ratliff
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
| Introduction 
-------------------------------------------------------------------------------
  Java GGEncoder is an encoding and decoding program for game genie codes. It
  can take game genie codes, or their decoded equivalents, and produce the
  alternate version. In other words, you can take game genie codes and turn
  them into "raw" codes (addresses, values, and sometimes compares), and vice
  versa.
  
-------------------------------------------------------------------------------
| Random Information
-------------------------------------------------------------------------------
  I wrote this program for several reasons. The most basic of which is as a
  tool for ROM hacking.
  
  You might say, aren't there several other programs which already do this?
  Yes, and no. uggconv will do all the translations, but has no graphical
  mode. ggconvc has a graphical mode, but doesn't do all the translations (it
  lacks a gameboy/gamegear mode). uggconv could be compiled for any system,
  but you still have to do that yourself (except on Windows). ggconvc will only
  work in Windows (it's written with Microsoft GUI Libraries). There are a few
  other programs that do some other interesting things that Java GGEncoder
  doesn't do. There is a game gear program which will patch ROMs for you. But
  it's limited to game gear ROMs (and possibly SMS roms). In a future version,
  I may add ROM patching support, as that seems like a natural extention.
  
-------------------------------------------------------------------------------
| Why is this program better?
-------------------------------------------------------------------------------
  First, it's written in Java. It will run on nearly any platform that is of
  any popularity at all. Windows, Mac OS-X, Linux, Solaris, etc. The text mode
  depends upon regular expressions, which were added in Java 1.4, so the text
  mode does not run on Mac OS classic. But the applet is written entirely in
  Java 1.1, so it should. I don't have a Mac with OS classic, so I cannot test
  this, but it should theoretically work.
  
  Second, the applet will run in a browser from anywhere in the world on nearly
  any platform. You don't even have to download it if you use the GUI version.
  
  Third, there is a text mode AND a GUI mode. You can script the text mode if
  you like to do mass-encoding. Or you can enjoy the convenience of the GUI.
  
  Fourth, it works on every platform the Game Genie was created for. Nintendo,
  Super Nintendo, Genesis/Megadrive, Game Boy, and Game Gear.
  
-------------------------------------------------------------------------------
| What's Missing
-------------------------------------------------------------------------------
  Automatic ROM patching would be nice. But I'll have to learn about
  SecurityManager's before I could integrate that into the applet.
  
  ggconvc tells you what instruction the hex values correspond to. I don't know
  if that's useful to people or not. It's not useful to me, so I haven't tried
  to add that.
  
-------------------------------------------------------------------------------
| How to Use
-------------------------------------------------------------------------------
  You will need a Java runtime environment (JRE). For the text mode, it will
  need to be Java >= 1.4 because we use regular expressions. For the GUI mode,
  any JRE should work, but it's only been tested in Java 1.4.
  
  You can get a Java JRE from Sun. http://java.sun.com
  
  On the command line, you can start the program by typing
  java -jar ggencoder.jar
  
  This will get you the GUI mode. To use it in text mode, you'll need to supply
  some arguments.
  
  java -jar nintendo decode ATVATGSL
  Will decode the NES game genie code ATVATGSL into it's raw component parts.
  60:0466?B5. This means the value is 60, the address is 0466, and the compare
  value is B5. These values are always in hex.
  
  java -jar nintendo encode 60:0466?B5 will give you the code ATVATGSL.
  
  You can do SNES, Genesis, and Game Boy/Game Gear codes, too. Just change
  Nintendo to snes, or genesis, or gbgg.
  
  For some more help and examples, type
  java -jar ggencoder.jar --help

-------------------------------------------------------------------------------
| Revision History
-------------------------------------------------------------------------------

  Version 1.1 - November 16, 2004
	- fixed a bug in NES gg encoder where codes would be invalid on a real gg
  Version 1.0 - July 17, 2004
	- intial release
	- support for NES, SNES, Game Gear, Game Boy, and Genesis Codes
  
-------------------------------------------------------------------------------
| Credits and Thanks
-------------------------------------------------------------------------------
  Merlyn LeRoy - Process for decypting Genesis/Megadrive codes
  Game Genie Code Creator's Club - Process for decrypting SNES codes
  Benzene - Process for decrypting NES codes
  The Genie's Bottle - Process for decrypting Game Boy / Game Gear Codes
  Nick Joslin? - He may be the author of the document from the Genie's Bottle
  		 that I read, but the site is gone, and I no longer have the
  		 original document.
  Wyrmcorp - Authors of uggconv. I tested my results against theirs
  Cheatmaster - Author of ggconvc - The whole idea came from her program
  Tony Hedstrom - His Codes gave me the idea to make some of my own, which lead
  		  to needing an encoding program, which lead to this
  Johnny Hang Yang - His questions made me take a closer look at the ignored part
                     of NES addresses and lead me to fix a bug in the program.

-------------------------------------------------------------------------------
| How it Works
-------------------------------------------------------------------------------
  The source code is here if you want to know how the program works. If you
  want to know how the decryptions work, take a look at the docs in the
  docs/ directory.
  
-------------------------------------------------------------------------------
| Problems, Questions, Comments?
-------------------------------------------------------------------------------
  You can contact emuWorks through our website http://games.technoplaza.net.
  There is an online feedback form if you like, or you can email us at
  webmaster ( A T ) technoplaza.net (remove the spaces and change it to @).

-------------------------------------------------------------------------------
| License
-------------------------------------------------------------------------------
  This program is licensed under the GNU GPL. You can read the license.txt file
  or visit http://www.gnu.org for more information.
