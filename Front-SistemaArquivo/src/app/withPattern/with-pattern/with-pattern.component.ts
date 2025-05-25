import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

interface Componente {
  tipo: 'arquivo' | 'pasta';
  id: string;
  nome: string;
  tamanho: number;
  componentes?: Componente[];
}

@Component({
  selector: 'app-with-pattern-recursive',
  standalone: true,
  imports: [CommonModule],
  template: `
    <ul>
      <li *ngFor="let node of nodes">
        <strong *ngIf="node.tipo === 'pasta'">📁 {{ node.nome }} ({{ node.tamanho }} bytes)</strong>
        <span *ngIf="node.tipo === 'arquivo'">📄 {{ node.nome }} ({{ node.tamanho }} bytes)</span>

        <app-with-pattern-recursive 
          *ngIf="node.tipo === 'pasta'" 
          [nodes]="node.componentes ?? []">
        </app-with-pattern-recursive>
      </li>
    </ul>
  `
})
export class WithPatternRecursiveComponent {
  @Input() nodes: Componente[] = [];
}

@Component({
  selector: 'app-with-pattern',
  standalone: true,
  imports: [CommonModule, WithPatternRecursiveComponent],
  template: `
    <app-with-pattern-recursive [nodes]="[payload]"></app-with-pattern-recursive>
  `
})
export class WithPatternComponent {
  payload: Componente = {
    tipo: "pasta",
    id: "44712dac-870b-4ef6-a249-28c1bfb046a7",
    nome: "raiz",
    tamanho: 250,
    componentes: [
      {
        tipo: "arquivo",
        id: "1df4d07a-0b76-4e16-876e-7b84e3e63be5",
        nome: "arquivo.txt",
        tamanho: 150
      },
      {
        tipo: "pasta",
        id: "ec781645-9021-4c77-a622-e672229ef062",
        nome: "secreta2",
        tamanho: 100,
        componentes: [
          {
            tipo: "arquivo",
            id: "48fc7eb9-a0f2-482f-b567-debaf283ba33",
            nome: "arquivoSecreto.txt",
            tamanho: 100
          }
        ]
      }
    ]
  };
}
