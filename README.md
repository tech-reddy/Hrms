# HRMS Project

This is a full-stack Human Resource Management System (HRMS) application built with Spring Boot, MySQL, Angular, and Bootstrap. The project supports three user roles—**Admin**, **Manager**, and **Employee**—and provides role-based modules and dashboards.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [User Roles & Modules](#user-roles--modules)
- [Important Credentials](#important-credentials)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Cloning the Repository](#cloning-the-repository)
  - [Backend Setup](#backend-setup)
  - [Frontend Setup](#frontend-setup)
- [Running the Application](#running-the-application)
- [Email Notification for New Employees](#email-notification-for-new-employees)
- [Additional Information](#additional-information)
- [License](#license)

## Overview

The HRMS project is designed to manage various HR functions including employee management, department and designation management, leave management, payroll management, and complaint management. The application has a modular architecture with separate modules for Admin, Manager, and Employee roles.

## Features

- **User Authentication & Role-Based Access:**
  - Three logins: Admin, Manager, Employee.
- **Admin Modules:**
  - Dashboard with HR metrics.
  - Employee management.
  - Department management.
  - Designation management.
  - Complaint management.
- **Manager Modules:**
  - Dashboard with HR metrics.
  - View employee list.
  - View department list.
  - Designation list.
  - Project management.
  - Payroll management.
  - Leave management.
  - Complaint management.
- **Employee Modules:**
  - Dashboard with personalized metrics.
  - My Projects.
  - Apply Leave.
  - My Payroll.
  - Complaints.
- **Email Notifications:**
  - When Admin creates an employee, the username and temporary password are sent to the employee’s registered email.

## User Roles & Modules

- **Admin:** Full access to all administrative functions such as managing employees, departments, designations, and complaints.
- **Manager:** Can view employee and department lists, manage projects, payroll, leave requests, and complaints.
- **Employee:** Can view personal projects, apply for leave, view payroll details, and submit complaints.

## Important Credentials

- **Admin Login:**
  - **Username:** `admin`
  - **Password:** `admin123`

## Tech Stack

- **Backend:** Spring Boot, MySQL, JPA/Hibernate
- **Frontend:** Angular, Bootstrap
- **Email:** JavaMailSender (for sending notifications)

## Getting Started

### Prerequisites

- Java JDK 21 or later
- Node.js and npm
- MySQL database
- IDE (e.g., IntelliJ IDEA, VS Code)

### Cloning the Repository

Clone the project repository using Git:

```bash
git clone https://github.com/yourusername/hrms-project.git
cd hrms-project
