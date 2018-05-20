# :computer: COMPUTER INFORMATION
## a small java library that helps you to obtain hardware specific properties

##### :paperclip: WHAT KIND OF PROPERTIES ?
Here are the complete listing :
- Host Name
- OS Name
- OS Version
- OS Manufacturer
- OS Configuration
- OS Build Type
- Registered Owner
- Registered Organization
- Product ID
- Original Install Date
- System Manufacturer
- System Model
- System Type
- Processor
- BIOS Version
- Windows Directory
- System Directory
- Boot Device
- System Locale
- Input Locale
- Time Zone
- Total Physical Memory
- Available Physical Memory
- Virtual Memory Max Size
- Virtual Memory Available
- Virtual Memory In Use
- Page File Location
- Domain
- Logon Server
- Hotfix
- Network Cards

##### :question: HOW TO USE ?
* Include the ComInfoLib.jar right into your active project
* write the below quick code
* choose which properties you'd like to


## :ok_hand: QUICK CODE
```
// put this import statement in the top of your code
import utils.ComputerInformation;

// use the below code for the execution
ComputerInformation machine = new ComputerInformation();
machine.getOSName();
//or
machine.getTimeZone();
// and so on...
```


## :star2: CREDITS
- all of these works are returned back to [FGroupIndonesia.com](http://fgroupindonesia.com)
- more details please contact through **Whatsapp : +62857-9556-9337** for the proper consultation schedules.
