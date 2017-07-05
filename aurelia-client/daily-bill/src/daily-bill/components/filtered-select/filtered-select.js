import {bindable, bindingMode} from 'aurelia-framework';

export class FilteredSelect {
    @bindable selectedItem;
    @bindable items;
    constructor() {
    }
    attached() {
        if(this.items) {
            this.filteredItems = this.items;
        }
        if(this.selectedItem) {
            this.filterValue = this.selectedItem.name;
        }
    }

    searchByFilterValue(it, filterValues) {
        let condition = true;
        for(let s of filterValues) {
           condition = condition && it.name.toLowerCase().indexOf(s.toLowerCase()) >= 0;
        }
        return condition;
    }

    filterChange() {
        if(this.filterValue) {
            let delimeter = /\s+/;
            let filterValues = this.filterValue.split(delimeter).filter(i => i !== '');
            this.filteredItems = this.items.filter(it => this.searchByFilterValue(it, filterValues))
        } else {
            this.filteredItems = this.items;
        }
        let value = this.items.find((element, index, array) => element.name.toLowerCase() === this.filterValue.toLowerCase())
        if(value) {
            this.selectedItem = value;
            this.filterSelectedItem = value;
        } else {
            this.selectedItem = {
                id: null,
                name: this.filterValue
            }
        }
    }

    selectItem() {
        this.filterValue = this.filterSelectedItem.name;
        this.selectedItem = this.filterSelectedItem;
    }
}
