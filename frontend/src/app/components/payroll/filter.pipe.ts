// filter.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter',
  standalone: false
})
export class FilterPipe implements PipeTransform {
  transform(items: any[], filterValue: string, propertyName: string): any[] {
    if (!items) {
      return [];
    }
    return items.filter(item => {
      return item[propertyName] === filterValue;
    });
  }
}
