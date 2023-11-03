import { Component, OnInit, ViewEncapsulation, Inject } from '@angular/core';
import { StockService } from './stock.service';

export interface Product {
  name: string;
  description: string;
  price: number;
  quantity: number;
  tax: number;
}

const STOCK_DATA: Product[] = [
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
  { name: "Product", description: 'Hydrogen', price: 1.0079, quantity: 10, tax: 0.01 },
];

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  encapsulation: ViewEncapsulation.None,
})

export class HomeComponent implements OnInit {

  constructor(private stockService: StockService, public dialog: MatDialog) { }

  ngOnInit(): void {
    this.getAllStockProducts();
  }

  displayedColumns: string[] = ['name', 'description', 'price', 'tax', 'quantity'];
  products: Product[] = STOCK_DATA;
  searchText = "";

  getAllStockProducts() {
    this.stockService.getAllStockProducts().subscribe((products: Product[]) => {
      this.products = products;
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogAddProduct, {
      data: { name: "teste", animal: "teste" },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}

import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule, MatDialog } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'dialog-add-product',
  templateUrl: 'dialog-add-product.html',
  standalone: true,
  imports: [MatDialogModule, MatFormFieldModule, MatInputModule, FormsModule, MatButtonModule],
})
export class DialogAddProduct {
  constructor(
    public dialogRef: MatDialogRef<DialogAddProduct>,
    @Inject(MAT_DIALOG_DATA) public data: Product,
  ) { }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
