package ar.com.ada.sb.relationship.component.data;

import ar.com.ada.sb.relationship.model.entity.Film;
import ar.com.ada.sb.relationship.model.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FilmLoaderData implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilmLoaderData.class);

    @Autowired
    @Qualifier("filmRepository")
    private FilmRepository filmRepository;

    @Value("${spring.application.env}")
    private String appEnv;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LOGGER.info("Enviroment" + appEnv);
        if (appEnv.equals("dev") || appEnv.equals("qa")) {
            LOGGER.info("Loading Actor initial data ... ");
            List<Film> filmList = Arrays.asList(
                    new Film("Lucy", "Lucy es una película francesa de acción y ciencia ficción de 2014 dirigida y escrita por Luc Besson y producida por EuropaCorp y Groupe TF1. El rodaje tuvo lugar en Taipéi, París y Nueva York."),
                    new Film("Marriage Story", "Un director de teatro y su mujer, actriz, luchan por superar un divorcio que les lleva al extremo tanto en lo personal como en lo creativo."),
                    new Film("Just Go with It", "El cirujano Danny decide contratar a su ayudante Katherine, una madre soltera con hijos, para que finjan ser su familia. Su intención es demostrarle a Palmer que su amor por ella es tan grande que está a punto de divorciarse de su mujer."),
                    new Film("The Break-Up", "Cuando Brooke, una comerciante de arte, y Gary, el chofer de un autobús turístico, finalmente deciden terminar con su relación, ninguno de los dos quiere salirse de su condominio. Sus amigos les sugieren una serie de tácticas que fallan para cansar a sus respectivas parejas, y la única solución es convertirse en un par de hostiles compañeros de casa."),
                    new Film("Troya", "Cuando Paris secuestra a Helena de Troya, el feroz guerrero Aquiles dirige a las fuerzas griegas en la batalla de Troya."),
                    new Film("Seven", "Somerset es un solitario y veterano detective a punto de retirarse que se encuentra con Mills, un joven impulsivo. Ambos investigan un particular asesinato. Es este el primero de una serie de crímenes que aluden los siete pecados capitales."),
                    new Film("The Departed", "Billy Costigan (Leonardo DiCaprio), policía del Sur de Boston, trabaja de forma encubierta para infiltrarse en la organización de la pandilla del jefe Frank Costello (Jack Nicholson). Como Billy se gana la confianza del gángster, un criminal de carrera llamado Colin Sullivan (Matt Damon) se infiltra en la policía y reporta sus actividades a los jefes de su sindicato. Cuando ambas organizaciones descubren que tienen a un enemigo en sus filas, Billy y Colin deben descubrir sus identidades."),
                    new Film("Revenant", "El trampero Hugh Glass intenta vengarse de sus compañeros, quienes lo abandonaron herido en el bosque después de que lo atacó un oso, pensando que moriría, víctima de las lesiones y de la crudeza del entorno y del invierno. Sin embargo, el rencor que siente es más fuerte que cualquier adversidad, y Glass no permitirá que nada ni nadie le prive de saciar su odio.")
            );

            filmList.forEach(film -> filmRepository.save(film));
        }
    }
}