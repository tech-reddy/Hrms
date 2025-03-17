import { Component, OnInit } from '@angular/core';
import { Project, ProjectStatus } from '../../../models/employee';
import { ProjectService } from '../../../services/project-tasks/project.service';

@Component({
  selector: 'app-project-list',
  standalone: false,
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.scss'
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  filteredProjects: Project[] = [];
  searchTerm = '';
  statusFilter = '';

  projectStatuses: string[] = Object.values(ProjectStatus);

  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.loadProjects();
  }

  loadProjects() {
    this.projectService.getAllProjects().subscribe(projects => {
      this.projects = projects;
      this.filteredProjects = projects;
    });
  }

  applyFilters() {
    this.filteredProjects = this.projects.filter(project => {
      const matchesSearch = project.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesStatus = this.statusFilter ? project.status === this.statusFilter : true;
      return matchesSearch && matchesStatus;
    });
  }

  statusBadgeClass(status: string): string {
    switch(status.toUpperCase()) {
      case 'PLANNED': return 'bg-secondary';
      case 'IN_PROGRESS': return 'bg-info';
      case 'COMPLETED': return 'bg-success';
      case 'ON_HOLD': return 'bg-warning';
      case 'CANCELLED': return 'bg-danger';
      default: return 'bg-light';
    }
  }
  
}