import { Component, Inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {MatRadioModule} from '@angular/material/radio';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SistemaArquivoService } from '../../service/sistema-arquivo.service';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Componente } from '../../model/componente';

@Component({
  selector: 'app-create-component',
  imports: [MatRadioModule, MatFormFieldModule, ReactiveFormsModule , CommonModule, MatInputModule, MatButtonModule],
  templateUrl: './create-component.component.html',
  styleUrl: './create-component.component.css'
})
export class CreateComponentComponent {
  createComponentForm: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA)
    public path: string,
    private formBuilder: FormBuilder, 
    private service: SistemaArquivoService,
    private dialogRef: MatDialogRef<CreateComponentComponent>){
    this.createComponentForm = this.formBuilder.group({
      tipo: [null, Validators.required],
      id: [, ],
      nome: [, Validators.required],
      tamanho: [, Validators.min(0)],
      componentes: [,]
    })
  }

  onSubmit(): void{
    if (this.createComponentForm.invalid) return;

    const data = this.createComponentForm.value;
    const componente = {
      tipo: data.tipo,
      id: data.id,
      nome: data.nome,
      ...(data.tipo === 'arquivo' ? { tamanho: data.tamanho } : { componentes: [], tamanho: 0 })
    };

    this.service.adicionarComponente(this.path, componente).subscribe({
      next: () => {this.dialogRef.close(true);},
      error: (err) => console.error("Erro ao deletar:", err)
      })
  }

  selectedTipo: string | null = null;

  onTipoChange(event: any) {
    const valor = event.value;

    if (this.selectedTipo === valor) {
      this.selectedTipo = null;
      this.createComponentForm.get('tipo')?.setValue(null);
    } else {
      this.selectedTipo = valor;
      this.createComponentForm.get('tipo')?.setValue(valor);
    }
  }

  allowOnlyNumbers(event: KeyboardEvent): void {
    const charCode = event.key.charCodeAt(0);
    if (charCode < 48 || charCode > 57) {
      event.preventDefault();
    }
  }

  blockPaste(event: ClipboardEvent): void {
    const pastedInput: string = event.clipboardData?.getData('text') || '';
    if (!/^\d+$/.test(pastedInput)) {
      event.preventDefault();
    }
  }

}
