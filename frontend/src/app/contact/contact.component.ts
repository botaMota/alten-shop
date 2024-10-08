import { CommonModule } from "@angular/common";
import {
  Component,
  inject,
  ViewEncapsulation,
} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: "app-contact",
  templateUrl: "./contact.component.html",
  styleUrls: ["./contact.component.scss"],
  standalone: true,
  imports: [
    FormsModule,
    ButtonModule,
    InputTextModule,
    InputTextareaModule,
    CommonModule,
    ToastModule
  ],
  encapsulation: ViewEncapsulation.None
})
export class ContactComponent {


  private readonly messageService = inject(MessageService);
  contactModel = new Contact('','')
  
  onSend() {
    console.log("Message sent");
    this.messageService.add({severity: 'info', summary:  'Success', detail: 'Demande de contact envoyée avec succès' });
  }
}

//user-data.ts
export class Contact {
	constructor(
	public mail: string,
	public message: string
	){}
}
