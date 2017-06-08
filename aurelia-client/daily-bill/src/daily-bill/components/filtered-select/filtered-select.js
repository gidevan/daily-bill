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

    filterChange() {
        if(this.filterValue) {
            this.filteredItems = this.items.filter(it => it.name.toLowerCase().indexOf(this.filterValue.toLowerCase()) >= 0)
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
