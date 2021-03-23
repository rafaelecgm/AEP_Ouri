package tp_ouri;

import java.io.IOException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author Suricato
 * @since 2021
 */
public class Verificar {
	/** call rules method */
	@Test
	  public void chamarMetodoDeRegras() {
	
		  OuriRegras.exibirRegasDoJogo();
	  }
          /** player versus computer
     * @throws java.io.IOException */
	  @Test
	  public void chamarJogoContraComputador() throws IOException {
		
		  jogadorAtual jogarOuriGame = new jogadorAtual(null, null,"S");
		  jogarOuriGame.jogarOuri();
	  }
	  
          /** what to do before the test */
	  @BeforeTest
	  public void beforeTest() {
	  }

          /** what to do after the test */
	  @AfterTest
	  public void afterTest() {
	  }

}