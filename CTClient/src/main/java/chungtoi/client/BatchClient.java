/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import chungtoi.client.proxy.ChungToi;

/**
 *
 * @author roland
 */
public class BatchClient {

    private ChungToi port;

    public BatchClient(ChungToi servicePort) {
        this.port = servicePort;
    }

    public void runParallel(List<String> tests) {
        List<Thread> threads = new ArrayList<>();

        // Prepara threads
        for(String test : tests) {
            threads.add(new Thread() {
                @Override
                public void run() {
                    try {
                        executaTeste(test);
                    } catch (IOException ex) {
                        System.out.println("Falha na execução do teste.");
                        ex.printStackTrace(System.out);
                    }
                }
            });
        }

        // Roda threads
        for(Thread t : threads) {
            t.start();
        }

        for(Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.out.println("Falha ao reagrupar thread.");
                ex.printStackTrace(System.out);
            }
        }
        System.out.println("Teste paralelo concluido.");
    }

    public void runSequential(List<String> tests) {
        for(String test : tests) {
            try {
                executaTeste(test);
            } catch (IOException ex) {
                System.out.println("Falha ao executar teste " + test);
                ex.printStackTrace(System.out);
            }
        }
        System.out.println("Teste sequencial concluido.");
    }

    private int preRegistro(java.lang.String j1, int id1, java.lang.String j2, int id2) {
        return port.preRegistro(j1, id1, j2, id2);
    }

    public void executaTeste(String rad) throws IOException {
        String inFile = rad + ".in";
        FileInputStream is = new FileInputStream(new File(inFile));
        //System.setIn(is);

        String outFile = rad + ".out";
        FileWriter outWriter = new FileWriter(outFile);
        try (PrintWriter out = new PrintWriter(outWriter); Scanner leitura = new Scanner(is)) {
            int numOp = leitura.nextInt();
            for (int i=0;i<numOp;++i) {
                System.out.print("\r"+rad+": "+(i+1)+"/"+numOp);
                int op = leitura.nextInt();
                String parametros = leitura.next();
                String param[] = parametros.split(":",-1);
                switch(op) {
                    case 0:
                        if (param.length!=4)
                            erro(inFile,i+1);
                        else
                            out.println(port.preRegistro(param[0],Integer.parseInt(param[1]),param[2],Integer.parseInt(param[3])));
                        break;
                    case 1:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.registraJogador(param[0]));
                        break;
                    case 2:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.encerraPartida(Integer.parseInt(param[0])));
                        break;
                    case 3:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.temPartida(Integer.parseInt(param[0])));
                        break;
                    case 4:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.obtemOponente(Integer.parseInt(param[0])));
                        break;
                    case 5:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.ehMinhaVez(Integer.parseInt(param[0])));
                        break;
                    case 6:
                        if (param.length!=1)
                            erro(inFile,i+1);
                        else
                            out.println(port.obtemTabuleiro(Integer.parseInt(param[0])));
                        break;
                    case 7:
                        if (param.length!=3)
                            erro(inFile,i+1);
                        else
                            out.println(port.posicionaPeca(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2])));
                        break;
                    case 8:
                        if (param.length!=5)
                            erro(inFile,i+1);
                        else
                            out.println(port.movePeca(Integer.parseInt(param[0]),Integer.parseInt(param[1]),Integer.parseInt(param[2]),Integer.parseInt(param[3]),Integer.parseInt(param[4])));
                        break;
                    default:
                        erro(inFile,i+1);
                }
            }
            System.out.println("... terminado!");
            out.close();
            leitura.close();
        }
    }

    private void erro(String arq,int operacao) {
        System.err.println("Entrada invalida: erro na operacao "+operacao+" do arquivo "+arq);
        System.exit(1);
    }
}
