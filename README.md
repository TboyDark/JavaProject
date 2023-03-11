![](https://img.shields.io/badge/Project%20Manager-Maven-blue?style=for-the-badge&logo=maven)
![](https://img.shields.io/github/repo-size/TboyDark/JavaProject?style=for-the-badge)
![](https://img.shields.io/github/languages/top/TboyDark/JavaProject?style=for-the-badge)


# Java Project

Welcome To my Java project for Start 2 Impact, Backend Master.


# What it is this project?

This project is a Course reservation program for an invented startup named IncuDO.

## How do i use it?

the program will promt a menu, in this menu you'll find a num value from 0 to 5. Each number has a service:
- 1: Will Show All Courses.
- 2: You will book an existing course.
- 3: You will cancel a reservation.
- 4: You will create a new user.
- 5: You will export into the desktop, a file containing all the available courses.
- 0: Exit program.

## In depth description for the services.
- After you selected the "1" value, the program will get all the courses of the file courses.csv and then display them all into the console in this format: **ID, Name, Description, Date, Hours Length, Location, Available**.

- After you selected the "2" value, the program will ask you to enter the **Course ID** and the **User ID**.  the program checks if the Course ID exists. If exists it checks either the course is available or not,  if the cousres is not available, the program will alert you that you cannot book that course. Then if the user id you entered exists, the program will write a reservation into a reservation.csv file, then will change te aviability of the course From YES to NO.

- After you selected the "3" value, the program will ask your user data. You must remember the id that you assigned to your user, then you can input your data as follows: **ID, Name, Surname, Date of birth, Address, Document ID**. After you entered your data, the program will check if your data corresponds to one user written into the user.csv file. if the user doesn't exists the program will alert you and you'll have to redo the operation. If the user data you insered are correct, the program will cancel the reservation written into reservation.csv file, will search the corresponded Course you booked and will set the aviability NO to YES.

- After you selected the "4" value, the program will inform you which ID you'll have to set and the you can proceed to enter your user data as follows:  **ID, Name, Surname, Date of birth, Address, Document ID**. Then the program will write the new user into the user.csv file.

- After you selected the "5" value, the program will create a **reservation_dd_MM_yyyy** file which contains all the **available Courses**.

## How to compile this project?

You can compile this project as follows: after you downloaded this project and exported into a folder, you have to open the cmd window, write :
``` bash
cd /C C:/path/to/project/folder
```
when the cmd has changed the directory of the folder, you have to write:
``` bash
mvn clear compile package
```
the following line, will delete the target folder of the project, recompile all the .java files into the .class file in the target directory and then will create the ReservationApp.jar into the target folder.

## How to execute the jar file?

You can exectue the jar file as follows:
``` bash
java -jar target/ReservationApp.jar
```
after you press enter, the program will start.

##  CSV Files.

You have to import your CSV File into the folder:
/projectFolder
	----/src
		------/main
		--------/resources


# Authors

[@TboyDark](https://github.com/TboyDark), My twitter [@TboyDark98](https://twitter.com/TboyDark98) Email: tommasobaldan1998@gmail.com .

## Acknowledgments
- [Start 2 Impact](https://www.start2impact.it/)
- [Giuseppe Scaramuzzino](https://www.youtube.com/@GiuseppeScaramuzzino)
- Web Guides

