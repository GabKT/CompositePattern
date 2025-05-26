import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';

interface Componente {
  tipo: 'arquivo' | 'pasta';
  id: string;
  nome: string;
  tamanho: number;
  componentes?: Componente[];
}

@Component({
  selector: 'app-with-pattern',
  standalone: true,
  imports: [CommonModule,
    MatTreeModule, MatButtonModule, MatIconModule, MatToolbarModule, 
  ],
  templateUrl: './with-pattern.component.html',
  styleUrl: './with-pattern.component.css'
})
export class WithPatternComponent {

  childrenAccessor = (node: Componente) => node.componentes ?? [];

  hasChild = (_: number, node: Componente) => !!node.componentes && node.componentes.length > 0;

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
          },
          {
            tipo: "pasta",
            id: "ec781645-9021-4c77-a622-e672229ef062",
            nome: "papacapim",
            tamanho: 100,
            componentes: [
              {
                tipo: "arquivo",
                id: "48fc7eb9-a0f2-482f-b567-debaf283ba33",
                nome: "arquivao.txt",
                tamanho: 100
              },
              {
                tipo: "pasta",
                id: "ec781645-9021-4c77-a622-e672229ef062",
                nome: "axamarisca",
                tamanho: 0,
                componentes: []
              }
            ]
          }
        ]
      }
    ]
  };

  dataSource = [this.payload];
}
