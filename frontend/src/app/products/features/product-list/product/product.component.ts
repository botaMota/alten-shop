import { Component, inject, Input, OnInit, signal } from "@angular/core";
import { TagModule } from 'primeng/tag';
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { KEYS, Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { DataViewModule } from 'primeng/dataview';

const emptyProduct: Product = {
    id: 0,
    code: "",
    name: "",
    description: "",
    image: "",
    category: "",
    price: 0,
    quantity: 0,
    internalReference: "",
    shellId: 0,
    inventoryStatus: "INSTOCK",
    rating: 0,
    createdAt: 0,
    updatedAt: 0,
};

@Component({
    selector: "app-product",
    templateUrl: "./product.component.html",
    styleUrls: ["./product.component.scss"],
    standalone: true,
    imports: [CardModule, TagModule, ButtonModule, DataViewModule],
})
export class ProductComponent implements OnInit {

    @Input()product!: Product;
    public isDialogVisible = false;
    public isCreation = false;

    private readonly productsService = inject(ProductsService);

    public readonly products = this.productsService.products;
    public readonly editedProduct = signal<Product>(emptyProduct);


    public shoppingCartList: number[] = [];

    ngOnInit(): void {

    }

    public onUpdate(product: Product) {
        this.isCreation = false;
        this.isDialogVisible = true;
        this.editedProduct.set(product);
    }

    public onDelete(product: Product) {
        this.productsService.delete(product.id).subscribe();
    }

    public onSave(product: Product) {
        if (this.isCreation) {
            this.productsService.create(product).subscribe();
        } else {
            this.productsService.update(product).subscribe();
        }
        this.closeDialog();
    }

    public onCancel() {
        this.closeDialog();
    }

    private closeDialog() {
        this.isDialogVisible = false;
    }





    public onAddToShoppingCart(productId: number) {


        const jsonString = window.localStorage.getItem(KEYS.SHOPPINGCART);
        this.shoppingCartList = jsonString ? JSON.parse(jsonString) : null;

        if (this.shoppingCartList) {
            if (!this.shoppingCartList.includes(productId)) {
                this.shoppingCartList.push(productId);
            } else {
                this.shoppingCartList = this.shoppingCartList.filter(num => num !== productId)
            }
        } else {
            this.shoppingCartList = [];
            this.shoppingCartList.push(productId);
        }
        console.log(this.shoppingCartList);
        const str = JSON.stringify(this.shoppingCartList);
        window.localStorage.setItem(KEYS.SHOPPINGCART, str)

    }

    getSeverity(product: Product) {
        if (product.inventoryStatus == 'INSTOCK') {
            return 'success';
        } else if (product.inventoryStatus == 'LOWSTOCK') {
            return 'info';
        } else {
            return 'danger';
        }
    }

    toggleLabel(productId: number) {
        const jsonString = window.localStorage.getItem(KEYS.SHOPPINGCART);
        this.shoppingCartList = jsonString ? JSON.parse(jsonString) : null;

        if (this.shoppingCartList) {
            if (this.shoppingCartList.includes(productId)) {
                return "Remove";
            }
            return 'Add to';
        }
    }

    toggleSeverity(productId: number) {
        const jsonString = window.localStorage.getItem(KEYS.SHOPPINGCART);
        this.shoppingCartList = jsonString ? JSON.parse(jsonString) : null;

        if (this.shoppingCartList) {
            if (this.shoppingCartList.includes(productId)) {
                return "danger";
            }
            return 'success';
        }
    }
}