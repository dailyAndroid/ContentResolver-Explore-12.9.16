# ContentResolver-Explore-13.9.16

Few things to keep in mind with this application 

CODE NUMBERED 1: 
apparently since the update of the new OS, the line of code 
"Cursor cursor = contentResolver.query(uri, null, null, null, null)" will cause an access denial error.
Therefore, in order to access the contact, we must use CODE NUMBERED 2. 

However initially, CODE NUMBERED 2 only displays the contact's name and not the number nor the picture. So i replaced 
the code with codes from CODE NUMBERED 1 to resolve such issue. 
