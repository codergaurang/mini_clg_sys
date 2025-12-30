# mini_clg_sys

A Java **console-based** mini college management system with separate flows for Admin, Teacher, and Student, backed by a database layer and SQL scripts.

## Features
- Role-based menus (Admin / Teacher / Student)
- Authentication module
- Database connectivity layer
- Console menu navigation

## Project structure
- `src/app/Main.java` — Entry point.  
- `src/app/ConsoleMenus.java` — Console menu handling.  
- `src/app/auth/` — Login/auth-related logic.  
- `src/app/db/` — Database connection / DB utilities.  
- `src/app/admin/` — Admin features.  
- `src/app/teacher/` — Teacher features.  
- `src/app/student/` — Student features.  
- `sql/` — SQL scripts for schema/data.

## Prerequisites
- Java (JDK 8+ recommended)
- A SQL database (check the `sql/` folder for the scripts and adapt to your DB)
- (Optional) An IDE like IntelliJ / Eclipse / VS Code

## Setup & Run
1. Clone the repo:
