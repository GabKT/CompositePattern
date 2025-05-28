import { ChangeDetectorRef, Component } from '@angular/core';
import { Componente } from '../../model/componente';
import { MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import { CreateComponentComponent } from '../../withPattern/create-component/create-component.component';
import { SistemaArquivoService2 } from '../../service/sistema-arquivo.service2';

@Component({
  selector: 'app-no-pattern',
  imports: [MatTreeModule, MatIconModule, MatButtonModule, MatToolbarModule, CommonModule],
  templateUrl: './no-pattern.component.html',
  styleUrl: './no-pattern.component.css'
})
export class NoPatternComponent {

  payload!: Componente;
  dataSource: Componente[] = [];
  selectedNode: any = null;

  childrenAccessor = (node: Componente) => node.componentes ?? [];

  hasChild = (_: number, node: Componente) => !!node.componentes && node.componentes.length > 0;

  constructor(private service: SistemaArquivoService2, private cdr: ChangeDetectorRef, private dialog: MatDialog,) { }

  ngOnInit() {
    this.service.getRaiz().subscribe({
      next: (data: Componente)=>{
        this.payload = data;
        this.dataSource = [this.payload];
        this.cdr.detectChanges();
      },
      error: (err) => console.log ("Erro getRaiz", err)
    })
  }

  onNodeClick(node: any) {
    this.selectedNode = node;
  }


  isPastaSelected(): boolean {
    return this.selectedNode?.tipo === 'pasta';
  }


  deletar(node: Componente) {
    const caminho = this.service.getCaminhoCompleto(node, this.payload);
    this.service.deletarComponente(caminho).subscribe({
      next: () => this.ngOnInit(),
      error: (err) => console.error("Erro ao deletar:", err)
    });
  }

  adicionar(): void{
    if (!this.selectedNode || this.selectedNode.tipo !== 'pasta') {
    alert('Selecione uma pasta para adicionar o componente!');
    return;
    }
    const dialogRef = this.dialog.open(CreateComponentComponent, {
      width: '60%',
      height: '60%',
      data: {
        path: this.service.getCaminhoCompleto(this.selectedNode, this.payload),
        usar8081: true
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        window.location.reload();
      }
    });
  }


}
