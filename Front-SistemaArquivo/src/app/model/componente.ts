export interface Componente {
  tipo: 'arquivo' | 'pasta';
  id: string;
  nome: string;
  tamanho: number;
  componentes?: Componente[];
}