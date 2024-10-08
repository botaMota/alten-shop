import {
  Component,
  inject,
  OnInit,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { TagModule } from 'primeng/tag';
import { KEYS, Product } from "./products/data-access/product.model";
import { from, interval, Observable } from "rxjs";
import { DialogModule } from 'primeng/dialog';
import { ProductsService } from "./products/data-access/products.service";
import { CommonModule } from "@angular/common";
import { ProductComponent } from "./products/features/product-list/product/product.component";
import { ButtonModule } from 'primeng/button';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, TagModule, ToolbarModule, PanelMenuComponent, DialogModule, CommonModule, ProductComponent, ButtonModule],
})
export class AppComponent implements OnInit {


  title = "SHOP";
  badge: string = '';
  displayBadge: boolean = false;
  public isDialogVisible = false;
  private readonly productsService = inject(ProductsService);

  selectedProducts: Product[] = [];



  ngOnInit(): void {

    this.refreshData();
    this.productsService.badgeSubject.subscribe((val) => {
      this.refreshData();
    })
  }

  refreshData() {
    const jsonString = window.localStorage.getItem(KEYS.SHOPPINGCART);
    var shoppingCartList = jsonString ? JSON.parse(jsonString) : null;
    if (shoppingCartList) {
      this.badge = shoppingCartList.length;
      this.displayBadge = shoppingCartList.length > 0;

      this.productsService.get().subscribe(products => {
        products.forEach(product => {
          const includesObject = this.selectedProducts.some(obj => obj.id === product.id);
          if (shoppingCartList.includes(product.id) && !includesObject) {
            this.selectedProducts.push(product);
          }

          if (!shoppingCartList.includes(product.id) && includesObject) {
            this.selectedProducts = this.selectedProducts.filter(item => item.id !== product.id);
          }
        })
      });
    }
  }

  onDisplayShoppingList() {
    this.isDialogVisible = true;
  }

}
