package tp_ouri;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;



/** actual player class */
class jogadorAtual 
{
        
	int jogadorAtual = 0;
	GerenciarTabuleiro ouriTabuleiro;
	PerfilJogador[] listaJogadoresPorNome;
	int arrayReorganizarTabuleiro=0;

	
	public static void main(String[] args) throws IOException 
	{

		

		OuriRegras.exibirRegasDoJogo();
		jogadorAtual jogarOuriGame = new jogadorAtual("Rafael", null,"S");
		jogarOuriGame.jogarOuri();
	}

	/**
	 * @param nomePrimeiroJogador
	 * @param nomeSegundoJogador
	 */
	jogadorAtual(String nomePrimeiroJogador, String nomeSegundoJogador,String realinharTabuleiro) 
	{

		ouriTabuleiro = new GerenciarTabuleiro();
		ouriTabuleiro.configuracaoInicialParaJogar();
		listaJogadoresPorNome = new PerfilJogador[2];

		// If you want to change the jogador eONomeDoJogador,Have a look into Main Method
		// If user not passed second jogador eONomeDoJogador,It will be used as Computer Player

		listaJogadoresPorNome[0] = new PerfilJogador(nomePrimeiroJogador, 0);
		listaJogadoresPorNome[1] = new PerfilJogador(nomeSegundoJogador, 1);
		jogadorAtual = 0;

		/*
		 *  board reshuffle is all About: Providing user view, how user play on game board, they always start from Right to Left,
		 *  But Current Array is Start from Left to Right, So If the user is thinking  Pits 6, eventually Internally about Array Index 1 ,
		 *  Internal array movements will be determined based on this. So reshuffle will change user Pits to predefined Pits.
		 */

		if(realinharTabuleiro.toUpperCase()=="S") {
			arrayReorganizarTabuleiro=1;
		}else {
			arrayReorganizarTabuleiro=0;
		}
	}

	/** method do start the game */
	public void jogarOuri() throws IOException 
	{
                /** method to show the field */
		exibirTabuleiroOuri();
		while (!ouriTabuleiro.fimDeJogo()) 
		{
			String nomeDoJogadorDoArray;
			if(jogadorAtual==0)
			{
				nomeDoJogadorDoArray=listaJogadoresPorNome[0].getNome();	
			}else {
				nomeDoJogadorDoArray=listaJogadoresPorNome[1].getNome();	
			}

			/*
			 *  Calling Function: selecionarMovimento to Determine the Move, If users are human then ask for Input at main Console else best move will be determined.
			 */
			int posicaoNum=0;
			int misturarNumeroDePosicao = listaJogadoresPorNome[jogadorAtual].selecionarMovimento(ouriTabuleiro); 
			if(arrayReorganizarTabuleiro==1) {
				posicaoNum=doMisturarTabuleiro(misturarNumeroDePosicao,jogadorAtual);
			}else {
				posicaoNum=misturarNumeroDePosicao;
			}
			
				/*
				 *  Display Over Console, which jogador moved was along with Changed Mancala Board
				 */

			 System.out.println("Jogador [ " + nomeDoJogadorDoArray + " ] moveu de " + misturarNumeroDePosicao);

			//int posicaoNum = listaJogadoresPorNome[jogadorAtual].selecionarMovimento(ouriTabuleiro,jogadorAtual,arrayReorganizarTabuleiro); 

			// the move:-->  User Or Computer has Selected there Pits which need to be move , which are store in variable posicaoNum. 

			/*
			 *  If you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it.
			 *  If the last piece you drop is in your own store, you get a free turn. ( variable : selecionarIrNovamente store those information)
			 */
			boolean selecionarIrNovamente = ouriTabuleiro.doMovimentoDePosicao(jogadorAtual, posicaoNum);

			/*
			 *  Display Over Console, which jogador moved was along with Changed Mancala Board
			 */

			exibirTabuleiroOuri();

			if (!selecionarIrNovamente) 
			{	
				// If the current jogador does not go again,switch to the other jogador
				if (jogadorAtual == 0) 	
					jogadorAtual = 1;
				else
					jogadorAtual = 0;
			}else
			{
				//If the last piece you drop is in your own Mancala , User get a free turn.
				System.out.println("Jogador [ " + nomeDoJogadorDoArray + " ] jogará novamente.");
			}
		}
		/*
		 * The game ends when all six spaces on one side of the Mancala board are empty.
		 */

		// Game is over , ouriTabuleiro empty pedras,
		ouriTabuleiro.semPedrasNoOuri(); 

		// Display final ouriTabuleiro. 
		exibirTabuleiroOuri(); 			


		/*
		 * Below section determined Who is Winner And Winner Award goes to......
		 */
		if (ouriTabuleiro.pedrasNoOuri(0) > ouriTabuleiro.pedrasNoOuri(1)){
			System.out.println("************* Parabéns..! Venceste o jogo ******************************");
			System.out.println(listaJogadoresPorNome[0].getNome() + " vitória");
			System.out.println("*******************************************");
		}
		else if (ouriTabuleiro.pedrasNoOuri(0) < ouriTabuleiro.pedrasNoOuri(1)) {
			System.out.println("************* Parabéns..! Venceste o jogo ******************************");
			System.out.println(listaJogadoresPorNome[1].getNome() + " vitória");
			System.out.println("*******************************************");
		}
		else {
			System.out.println("************* Se a pontuação do jogo for igual, o jogo será empatado :) ******************************");
			System.out.println("Empate");// If this possible for safe side :)
			System.out.println("*******************************************");
		}

	}

	/**
	 * @param entradaPosicaoUtilizador
	 * @param jogadorAtual  informacoes do jogador atual
	 * @return
	 */
	public int doMisturarTabuleiro(int entradaPosicaoUtilizador,int jogadorAtual)
	{
		int novaPosicao = 0;
		if(jogadorAtual==0) {
			if(entradaPosicaoUtilizador==6)
				novaPosicao= 1;
			else if(entradaPosicaoUtilizador==5)
				novaPosicao= 2;
			else if (entradaPosicaoUtilizador==4)
				novaPosicao= 3;
			else if (entradaPosicaoUtilizador==3)
				novaPosicao= 4;
			else if (entradaPosicaoUtilizador==2)
				novaPosicao= 5;
			else if (entradaPosicaoUtilizador==1)
				novaPosicao= 6;
		}else {
			novaPosicao=entradaPosicaoUtilizador;
		}
		return novaPosicao;

	}
	/**
	 * 
	 */

	private void exibirTabuleiroOuri() 
	{

		/*
		 *  Function : displayMancalsBoardConsole
		 *  <<< This function get Called n times>>>, till game is not finished.
		 *  If you are able to see Console or Mancala Dashboard , this Function is created that, This function have 4 step which will
		 *  repeate each time or with each game move
		 *  step 1 : 	Display Player 2 Pits and Player 2 Information
		 *  Step 2 :  	Display Mancala information Both side
		 *  Step 3 : 	Display Player 1 Pits and Player 1 Information
		 *  Step 4 :	print Seperater ( ******* )
		 */

		try {
			/*
			 *  Have used the sleep method here, as Between 2 play, user have time to see and remember there move or Pits positions
			 */
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\n");
		String linhaOuriSeparador = ""; 
		System.out.println("*************************************");
		System.out.print("      ");

		//Step 1 : 	Display Player 2 Pits and Player 2 Information
		for (int i = 1; i <= GerenciarTabuleiro.jogandoPosicao; i++) 
		{
			System.out.print(ouriTabuleiro.pedrasNaPosicao(1, i) + "    ");
			linhaOuriSeparador += "     ";
		}

		//	Step 1.1 : 	Player 2 Information
		exibirJogador(1); 

		//	Step 2 :  	Display Mancala information Both side. Each jogador has a store (called a Mancala) to the right side of the Mancala board.

		System.out.print(ouriTabuleiro.pedrasNoOuri(1) + "    "); 
		System.out.print(linhaOuriSeparador);
		System.out.println(ouriTabuleiro.pedrasNoOuri(0));

		System.out.print("      ");

		//	Step 3 : 	Display Player 1 Pits and Player 1 Information
		for (int i = GerenciarTabuleiro.jogandoPosicao; i >= 1; i--)
			System.out.print(ouriTabuleiro.pedrasNaPosicao(0, i) + "    "); 

		//	Player 3.1 Information
		exibirJogador(0); 
		System.out.println("*************************************");
	}

	/**
	 * @param numeroJogadorOuri
	 */
	private void exibirJogador(int numeroJogadorOuri) 
	{
		/* 
		 *  In a Console User turn need to be printed which will indicate ,Who is Who and who is playing.
		 *  Below section is buld to determine the same
		 */

		// Check If it this jogador's turn,
		if (jogadorAtual == numeroJogadorOuri){  
			System.out.print("            -->> ");        // --> Sign mean User Turn, Active user, Same can be visble in Console screen
		}else {
			System.out.print("                 ");       // User is not active , user loose his/her turn, Same can be visble in Console screen
		}

		/*
		 *  Below section will added to show User/Player Information to Main Console eg.. user name or User Number
		 */
		int jogadorContador;
		if(numeroJogadorOuri==0) {
			jogadorContador=1;
		}else {
			jogadorContador=2;
		}

		System.out.println("Jogador " + jogadorContador + " ( " + listaJogadoresPorNome[numeroJogadorOuri].getNome() + ")");
	}





}


class GerenciarTabuleiro 
{
	RecolhePosicao[] posicao;
	static final int jogandoPosicao = 6, totalPosicao = 2 * (jogandoPosicao + 1);


	GerenciarTabuleiro() 
	{
		posicao = new RecolhePosicao[totalPosicao];
		for (int posicaoNum = 0; posicaoNum < totalPosicao; posicaoNum++)
			posicao[posicaoNum] = new RecolhePosicao();
	}

	public void configuracaoInicialParaJogar() 
	{
		/*
		 *  Add 4 Stones In each Pits ( 4*6=24 )
		 */
		for (int posicaoNum = 0; posicaoNum < totalPosicao; posicaoNum++)
			if (!eUmaChamadaDoOuri(posicaoNum))
				posicao[posicaoNum].adicionaPedra(4);
		/*
		 *  Any Number of Stones can be added in Pits
		 */
	}

	public int pedrasNoOuri(int jogadorNum) 
	{
		return posicao[getOuri(jogadorNum)].getPedras();
	}

	public int pedrasNaPosicao(int jogadorNum, int posicaoNum) 
	{
		return posicao[getPosicaoNum(jogadorNum, posicaoNum)].getPedras();
	}

	private int getPosicaoNum(int jogadorNum, int posicaoNum) 
	{
		return jogadorNum * (jogandoPosicao + 1) + posicaoNum;
	}

	private int getOuri(int jogadorNum) 
	{
		return jogadorNum * (jogandoPosicao + 1);
	}

	private boolean eUmaChamadaDoOuri(int posicaoNum) 
	{
		return posicaoNum % (jogandoPosicao + 1) == 0;
	}

	public GerenciarTabuleiro fazerUmaCopiaDoTabuleiro() 
	{
		GerenciarTabuleiro novoTabuleiro = new GerenciarTabuleiro();
		for (int posicaoNum = 0; posicaoNum < totalPosicao; posicaoNum++)
			novoTabuleiro.posicao[posicaoNum].adicionaPedra(this.posicao[posicaoNum].getPedras());
		return novoTabuleiro;
	}

	/**
	 * @param jogadorAtualNum
	 * @param posicaoEscolhidaNum
	 * @return
	 */
	public boolean doMovimentoDePosicao(int jogadorAtualNum, int posicaoEscolhidaNum) 
	{
		boolean posicaoChamada=false;
		/*
		 * If the last piece Player drop is in an empty hole on His/her side, Player capture that piece and any pieces in the hole directly opposite.
		 */
		int posicaoNum = getPosicaoNum(jogadorAtualNum, posicaoEscolhidaNum);
		int pedras = posicao[posicaoNum].removerPedras();
		while (pedras != 0) 
		{
			posicaoNum--;
			if (posicaoNum < 0)
				posicaoNum = totalPosicao - 1;
			if (posicaoNum != getOuri(outroJogadorNum(jogadorAtualNum))) 
			{
				posicao[posicaoNum].adicionaPedra(1);
				pedras--;
			}
			posicaoChamada=true;
		}
		
		if(pedras==0 && posicaoChamada==false) {
			/*
			 *  Player Selected Pits which have 0 pedras in, So Ask jogador to irNovamente and select again Pits, which value is >0;
			 */
			System.out.println("**********************************************************************************************************************************************");
			System.out.println("Cuidado -> Jogador [ "+( jogadorAtualNum+1)+" ] selecionou posição sem pedras (posição = 0), então o jogador precisa ir novamente e escolher posições no tabuleiro.");
			System.out.println("**********************************************************************************************************************************************");
			return true;
		}
		
		if (posicaoNum == getOuri(jogadorAtualNum))
			return true;
		if (quemEquem(posicaoNum) == jogadorAtualNum && posicao[posicaoNum].getPedras() == 1) 
		{
			pedras = posicao[posicaoJogadorInimigoNum(posicaoNum)].removerPedras();
			posicao[getOuri(jogadorAtualNum)].adicionaPedra(pedras);
		}
		return false;
	}

	private int quemEquem(int posicaoNum) 
	{
		return posicaoNum / (jogandoPosicao + 1);
	}

	private int posicaoJogadorInimigoNum(int posicaoNum) 
	{
		return totalPosicao - posicaoNum;
	}

	private int outroJogadorNum(int jogadorNum) 
	{
		if (jogadorNum == 0)
			return 1;
		else
			return 0;
	}

	public boolean fimDeJogo() 
	{
		for (int jogador = 0; jogador < 2; jogador++) 
		{
			int pedras = 0;
			for (int posicaoNum = 1; posicaoNum <= jogandoPosicao; posicaoNum++)
				pedras += posicao[getPosicaoNum(jogador, posicaoNum)].getPedras();
			if (pedras == 0)
				return true;
		}
		return false;
	}

	public void semPedrasNoOuri() 
	{
		for (int jogador = 0; jogador < 2; jogador++)
			for (int posicaoNum = 0; posicaoNum <= jogandoPosicao; posicaoNum++) 
			{
				int pedras = posicao[getPosicaoNum(jogador, posicaoNum)].removerPedras();
				posicao[getOuri(jogador)].adicionaPedra(pedras);
			}
	}


}


class RecolhePosicao 
{
	int pedras;
	
	RecolhePosicao() 
	{
		this.pedras = 0;
	}

	public int getPedras() 
	{
		return pedras;
	}

	public void adicionaPedra(int pedras) 
	{
		this.pedras += pedras;
	}

	public int removerPedras() {
		int pedras = this.pedras;
		this.pedras = 0;
		return pedras;
	}

	
}

class PerfilJogador 
{
	String eONomeDoJogador;
	int jogadorOuriNum;
	PerfilJogador(String nome, int jogadorNum) 
	{
		this.eONomeDoJogador = nome;
		this.jogadorOuriNum = jogadorNum;
	}

	public String getNome() 
	{
		if (eONomeDoJogador != null)
			return eONomeDoJogador;
		else
			return "Robbet!";
	}

	public int selecionarMovimento(GerenciarTabuleiro tabuleiro) throws IOException 
	{

		int posicaoNum = 0;
		if (eONomeDoJogador != null) 
		{ 
			/*
			 *  The below if will check : If Human is playing or Not, eONomeDoJogador!=null then , ask User to provide Input else calculate Move.
			 */
			// Real jogador - not the Robbet!
			try {


				System.out.println(" ______________________________________________________________________________");
                                System.out.println(" A ultima jogada será exibida após o ultimo turno do jogador.");
				System.out.println(" ______________________________________________________________________________");
				System.out.println(" ---Quem está a jogar, marque abaixo!---\n");
				Scanner scanner = new Scanner(System.in);
				System.out.print("<<<<<"+ eONomeDoJogador + ">>>>>  POR FAVOR INSIRA UMA POSIÇÃO PARA MOVER :->   "); // Prompt User Input need to be provided
				scanner:
					while(scanner.hasNext()) {
						if(scanner.hasNextInt()){
							posicaoNum = scanner.nextInt();
							if((posicaoNum<=6 && posicaoNum>=1))  {
								break scanner;
							} else {
								System.out.println("******************************************************************************");
								System.out.println("O JOGADOR INSERIU UMA POSIÇÃO INCORRETA... DEVE SELECIONAR ENTRE ( 1 TO 6 ), Por favor insira um valor correto abaixo");
								System.out.println("******************************************************************************");
								System.out.print("<<<<<"+ eONomeDoJogador + ">>>>>  POR FAVOR INSIRA UMA POSIÇÃO PARA MOVER :->   "); // Prompt User Input need to be provided
							}
						} else {
							System.out.println("******************************************************************************");
							System.out.println("ERROR: Entrada Inválida... DEVE SELECIONAR ENTRE ( 1 TO 6 ) e somente número, Informação está em formato incorreto");
							System.out.println("******************************************************************************");
							System.out.print("<<<<<"+ eONomeDoJogador + ">>>>>  POR FAVOR INSIRA UMA POSIÇÃO PARA MOVER :->   "); // Prompt User Input need to be provided
							scanner.next();
						}
					}

			} catch (NumberFormatException ex) {
				System.err.println("Não foi possivel converter " + ex.getMessage()+"\n");
			}	

			/*
			 *  Once user Input is validated Move return to Control flow.
			 */
			return posicaoNum; 
		}else {

			// Robbet is playing - need to determine best move
			int melhorMovimento = -1; // No best move initially
			int repetirMovimento = -1; // Or go again.
			int maxNovasPedras = -1; // no move has added pedras to the
			// mancala.
			// Trying the possible moves
			for (posicaoNum = 1; posicaoNum <= GerenciarTabuleiro.jogandoPosicao; posicaoNum++) 
			{
				if (tabuleiro.pedrasNaPosicao(jogadorOuriNum, posicaoNum) != 0) 
				{ // Only nonempty posicao may be
					// moved from
					GerenciarTabuleiro testarTabuleiro = tabuleiro.fazerUmaCopiaDoTabuleiro(); 	  // Make a copy of the ouriTabuleiro
					boolean irNovamente = testarTabuleiro.doMovimentoDePosicao(jogadorOuriNum, posicaoNum); // Try the move on the ouriTabuleiro copy.
					if (irNovamente==true) // If move allows us to go again,
						repetirMovimento = posicaoNum; // remember the move.
					int novasPedras = testarTabuleiro.pedrasNoOuri(jogadorOuriNum) - tabuleiro.pedrasNoOuri(jogadorOuriNum); // See how many pedras this move added to our mancala.
					if (novasPedras > maxNovasPedras) 
					{ 
						// More pedras than so far?
						maxNovasPedras = novasPedras; // Remember how many and the move.
						melhorMovimento = posicaoNum; 
					}
				}
			}

			// Tried all possibilities, return the best one
			if (maxNovasPedras > 1) // maxNovasPedras > 1 means a
				// multistone capture occurred.
				return melhorMovimento;
			else if (repetirMovimento != -1) // Barring that, use a "go
				// again".
				return repetirMovimento;
			else
				return melhorMovimento; // 1 or possibly 0 pedras added; oh well :)!
		}
	}


}










