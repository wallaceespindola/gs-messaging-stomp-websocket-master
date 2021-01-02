package websocket;

import java.math.BigDecimal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import utils.CSVUtil;
import utils.Fibonacci;

/**
 * Controller para testes de Websocket.
 * 
 * Projeto de Graduacao - Engenharia de Software - Poli/UFRJ
 * 
 * @author Wallace de Souza Espindola
 *
 */
@Controller
public class WebsocketController {

	private final String NOME_TESTE = "Websocket";

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public ConteudoMensagem executar(HelloMessage message) throws Exception {

		int numFibo = Integer.parseInt(message.getName());
		int contChamada = 1;
		long tInicioChamadas = System.currentTimeMillis();
		long tChamadaAtual = tInicioChamadas;
		long inicioProcesso = tInicioChamadas;
		long fimProcesso = 0;
		long resultado = 0;

		resultado = Fibonacci.fibo(numFibo); // processo a ser executado

		tChamadaAtual = System.currentTimeMillis();
		fimProcesso = System.currentTimeMillis();

		BigDecimal mil = new BigDecimal(1000);
		mil = mil.setScale(2, BigDecimal.ROUND_HALF_UP);

		long duracaoProcessoEmMilis = fimProcesso - inicioProcesso;
		BigDecimal decimalDuracaoProcessoEmMilis = new BigDecimal(duracaoProcessoEmMilis);
		decimalDuracaoProcessoEmMilis = decimalDuracaoProcessoEmMilis.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal decimalDuracaoProcessoEmSeg = decimalDuracaoProcessoEmMilis.divide(mil, BigDecimal.ROUND_HALF_UP);

		CSVUtil.salvarCSV(NOME_TESTE, numFibo, contChamada, tInicioChamadas, tChamadaAtual, inicioProcesso, fimProcesso, resultado);

		return new ConteudoMensagem("Fibo " + message.getName() + " executado em " + decimalDuracaoProcessoEmSeg + " segundos! (resultado=" + resultado + ")");
	}

}
