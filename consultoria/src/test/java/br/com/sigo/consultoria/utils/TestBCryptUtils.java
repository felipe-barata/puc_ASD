package br.com.sigo.consultoria.utils;

import br.com.sigo.consultoria.utils.cripto.BCryptUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBCryptUtils {

  @Autowired
  private BCryptUtils bCryptUtils;

  @Test
  public void testGerarBCrypt() {
    String s = bCryptUtils.gerarBCrypt("123456");
    Assertions.assertNotNull(s);
    Assertions.assertNotEquals("123456", s);
  }

  @Test
  public void testVerificarBCrypt() {
    String s = bCryptUtils.gerarBCrypt("123456");
    Assertions.assertTrue(bCryptUtils.verificarBCrypt("123456", s));
  }

}
