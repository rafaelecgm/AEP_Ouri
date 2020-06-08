package tp_ouri;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Verificar {
	
	@Test
	  public void chamarMetodoDeRegras() {
	
		  OuriRegras.exibirRegasDoJogo();
	  }
	  @Test
	  public void chamarJogoContraComputador() throws IOException {
		
		  jogadorAtual jogarOuriGame = new jogadorAtual(null, null,"S");
		  jogarOuriGame.jogarOuri();
	  }
	  
	  @BeforeTest
	  public void beforeTest() {
	  }

	  @AfterTest
	  public void afterTest() {
	  }

}