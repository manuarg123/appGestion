import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class PaginationService {

  constructor(private apiService: ApiService) { }

  onPageChange(pageNumber: number, currentPage: number, pageSize: number, callback: Function) {
    if (pageNumber !== currentPage) {
      currentPage = pageNumber;
      callback(currentPage);
    }
  }

  onNextPage(currentPage: number, pageSize: number, totalItems: number, callback: Function) {
    if (currentPage < Math.ceil(totalItems / pageSize)) {
      currentPage++;
      callback(currentPage);
    }
  }

  onPrevPage(currentPage: number, callback: Function) {
    if (currentPage > 1) {
      currentPage--;
      callback(currentPage);
    }
  }

  getTotalPages(totalItems: number, pageSize: number) {
    return Math.ceil(totalItems / pageSize);
  }
}
