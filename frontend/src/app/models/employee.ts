export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone?: string;
  // Represent dates as ISO strings (e.g., '2025-03-01')
  hireDate?: string;
  salary?: number;
  department?: Department;
  designation?: Designation;
  // Assuming Attendance will be defined later if needed\n  // attendances?: Attendance[];
  user?: User;
}

export interface Department {
  id: number;
  name: string;
  code: string;
  // Manager is an Employee; mark optional if not always available
  manager?: Employee;
}

export interface Designation {
  id: number;
  title: string;
  grade: string;
}

// Define roles as a type or enum depending on your application needs
export type Role = 'ADMIN' | 'MANAGER' | 'EMPLOYEE';

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  role: Role;
  // Reference back to the employee if needed\n  employee?: any;
  passwordResetRequired: boolean;
}


export interface LeaveType {
  id?: number;
  name: string;
  description: string;
  maxDays: number;
  carryForwardAllowed: boolean;
}

// leave-request.model.ts
export interface LeaveRequest {
  id?: number;
  employeeId: number;
  leaveTypeId: any;
  startDate: Date;
  endDate: Date;
  reason?: string;
}

// leave-request-response.model.ts
export interface LeaveRequestResponse {
  id: number;
  firstName: string;
  leaveType: string;
  startDate: Date;
  endDate: Date;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  reason?: string;
  appliedDate: Date;
}

export interface LeaveBalance {
  id: number;
  employee?: Employee; // optional
  leaveType?: LeaveType; // optional
  totalDays: number;
  usedDays: number;
  remainingDays: number;
}


export interface UserResponse {
  id: number;
  username: string;
  role: Role;
}


// project.model.ts
export interface Project {
  id: number;
  name: string;
  description?: string;
  startDate: Date;
  endDate: Date;
  status: ProjectStatus;
  manager: Employee;
  tasks: Task[];
  assignedEmployees: Employee[];
}

export interface CreateProjectRequest {
  name: string;
  description?: string;
  startDate: Date;
  endDate: Date;
  managerId: number;
  assignedEmployeeIds: number[];
}

export interface UpdateProjectRequest {
  name?: string;
  description?: string;
  endDate?: Date;
  status?: ProjectStatus;
  assignedEmployeeIds?: number[];
}

export enum ProjectStatus {
  PLANNING = 'PLANNING',
  IN_PROGRESS = 'IN_PROGRESS',
  ON_HOLD = 'ON_HOLD',
  COMPLETED = 'COMPLETED'
}

// task.model.ts
export interface Task {
  id: number;
  title: string;
  description?: string;
  priority: TaskPriority;
  status: TaskStatus;
  startDate: Date;
  endDate: Date;
  assignedEmployee: Employee;
  project: Project;
}

export interface CreateTaskRequest {
  title: string;
  description?: string;
  priority: TaskPriority;
  startDate: Date;
  endDate: Date;
  assignedEmployeeId: number;
}

export interface UpdateTaskRequest {
  title?: string;
  description?: string;
  priority?: TaskPriority;
  endDate?: Date;
  status?: TaskStatus;
  assignedEmployeeId?: number;
}

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH'
}

export enum TaskStatus {
  NOT_STARTED = 'NOT_STARTED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  BLOCKED = 'BLOCKED'
}

// models/complaint.model.ts
export interface ComplaintResponse {
  id: number;
  description: string;
  status: ComplaintStatus;
  response?: string;
  createdAt: Date;
}

export interface CreateComplaintRequest {
  description: string;
}

export interface UpdateComplaintRequest {
  status?: ComplaintStatus;
  response?: string;
}

export enum ComplaintStatus {
  OPEN = 'OPEN',
  IN_PROGRESS = 'IN_PROGRESS',
  RESOLVED = 'RESOLVED',
  CLOSED = 'CLOSED'
}

// payroll.model.ts
export interface PayrollResponse {
  id: number;
  firstName: string;
  payPeriodStart: Date;
  payPeriodEnd: Date;
  grossEarnings: number;
  totalDeductions: number;
  netSalary: number;
  paymentStatus: PaymentStatus;
  details: PayrollDetail[];
}

export interface PayrollDetail {
  componentName: string;
  componentType: ComponentType;
  amount: number;
}

export interface PayrollRequest {
  payPeriodStart: Date;
  payPeriodEnd: Date;
}

export enum PaymentStatus {
  PENDING = 'PENDING',
  PROCESSED = 'PROCESSED',
  FAILED = 'FAILED'
}

export enum ComponentType {
  EARNING = 'EARNING',
  DEDUCTION = 'DEDUCTION'
}