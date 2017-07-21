import {bindable, bindingMode} from 'aurelia-framework';

export class FilteredSelect {
    @bindable selectedItem;
    @bindable items;
    @bindable callbackFn;
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
        console.log('call callback');
        console.log(this.callbackFn);
        /*
        if(this.callbackFn) {
            this.callbackFn(this.selectedItem.id);
        }
        */
    }

    selectItem() {
        console.log('select item')
        this.filterValue = this.filterSelectedItem.name;
        this.selectedItem = this.filterSelectedItem;
        if(this.callbackFn) {
            this.callbackFn(this.selectedItem.id);
        }
    }
}
