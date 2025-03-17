export interface PaginatedResponse<T> {
  content: T[];
  pageable: any; // You can create a detailed interface for pageable if needed
  totalElements: number;
  totalPages: number;
  last: boolean;
  numberOfElements: number;
  size: number;
  number: number;
  sort: any;
  first: boolean;
  empty: boolean;
}