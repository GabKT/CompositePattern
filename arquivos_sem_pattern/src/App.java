public class App {
    public static void main(String[] args) {
        Pasta raiz = new Pasta("Raiz");
        Pasta pasta1 = new Pasta("Pasta1");
        Pasta pasta2 = new Pasta("Pasta2");
        Pasta pasta3 = new Pasta("Pasta3");
        Pasta pasta4 = new Pasta("Pasta4");

        Arquivo a1 = new Arquivo("arquivo1.txt", 10);
        Arquivo a2 = new Arquivo("arquivo2.txt", 100);
        Arquivo a3 = new Arquivo("arquivo3.txt", 1000);

        raiz.adicionarComponente(pasta1);
        raiz.adicionarComponente(pasta2);

        pasta1.adicionarComponente(a1);
        pasta2.adicionarComponente(a2);
        pasta2.adicionarComponente(pasta3);
        pasta3.adicionarComponente(pasta4);
        pasta4.adicionarComponente(a3);

        System.out.println("Estrutura inicial:\n\n");
        raiz.exibir("");

        a1.moverPara(pasta2);

        pasta2.renomear("Documentos");

        a2.renomear("renomeado.txt");

        pasta2.moverPara(pasta1);
        pasta2.moverPara(pasta4);
        pasta3.moverPara(pasta4);

        System.out.println("\nEstrutura após alterações:\n\n");
        raiz.exibir("");
    }
}
