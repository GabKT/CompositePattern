import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Componente } from '../model/componente';

@Injectable({
  providedIn: 'root'
})
export class SistemaArquivoService2 {

  private apiUrl = 'http://localhost:8081/ApiSF';

  constructor(private http: HttpClient) {}

  getRaiz(): Observable<Componente> {
    return this.http.get<Componente>(`${this.apiUrl}/raiz`);
  }

  adicionarComponente(caminhoDestino: string, componente: Componente): Observable<Componente> {
    const params = new HttpParams().set('caminhoDestino', caminhoDestino);
    return this.http.post<Componente>(`${this.apiUrl}/adicionar`, componente, { params });
  }

  moverComponente(origem: string, destino: string): Observable<Componente> {
    const params = new HttpParams()
      .set('origem', origem)
      .set('destino', destino);
    return this.http.post<Componente>(`${this.apiUrl}/mover`, null, { params });
  }

  deletarComponente(caminho: string): Observable<Componente> {
    const params = new HttpParams().set('caminho', caminho);
    return this.http.delete<Componente>(`${this.apiUrl}/delete`, { params });
  }

  renomearComponente(caminho: string, novoNome: string): Observable<Componente> {
    const params = new HttpParams()
      .set('caminho', caminho)
      .set('novoNome', novoNome);
    return this.http.post<Componente>(`${this.apiUrl}/renomear`, null, { params });
  }

  getCaminhoCompleto(node: Componente, raiz: Componente): string {
    const caminho: string[] = [];

    const encontrarCaminho = (atual: Componente, alvo: Componente, caminhoAtual: string[]): boolean => {
        caminhoAtual.push(atual.nome);

        if (atual === alvo) return true;

        if (atual.componentes) {
        for (const filho of atual.componentes) {
            if (encontrarCaminho(filho, alvo, caminhoAtual)) return true;
        }
        }

        caminhoAtual.pop();
        return false;
    };

    const caminhoAtual: string[] = [];
    encontrarCaminho(raiz, node, caminhoAtual);
    return caminhoAtual.join('/');
   }

}
