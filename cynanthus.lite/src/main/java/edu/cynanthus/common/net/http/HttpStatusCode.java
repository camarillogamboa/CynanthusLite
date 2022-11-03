package edu.cynanthus.common.net.http;

/**
 * <h1>Interfaz Http status.</h1>
 *
 * <h2>Códigos de estado de respuesta HTTP</h2>
 * <p>
 * Los códigos de estado de respuesta HTTP indican si se ha completado
 * satisfactoriamente una solicitud HTTP específica.
 * Las respuestas se agrupan en cinco clases:
 * </p>
 *
 * <ul>
 *     <li>Respuestas informativas (100–199),</li>
 *     <li>Respuestas satisfactorias (200–299),</li>
 *     <li>Redirecciones (300–399),</li>
 *     <li>Errores de los clientes(400–499),</li>
 *     <li>y errores de los servidores (500–599).</li>
 * </ul>
 */
public interface HttpStatusCode {

    /**
     * The constant CONTINUE.
     * <p>
     * Esta respuesta provisional indica que todo hasta ahora está bien y que el
     * cliente debe continuar con la solicitud o ignorarla si ya está terminada.
     */
    int CONTINUE = 100;

    /**
     * The constant SWITCHING_PROTOCOL.
     * <p>
     * Este código se envía en respuesta a un encabezado de solicitud Upgrade (en-US)
     * por el cliente e indica que el servidor acepta el cambio de protocolo propuesto por el agente de usuario.
     */
    int SWITCHING_PROTOCOL = 101;

    /**
     * The constant PROCESSING.
     * <p>
     * Este código indica que el servidor ha recibido la solicitud y aún se encuentra procesandola,
     * por lo que no hay respuesta disponible.
     */
    int PROCESSING = 102;

    /**
     * The constant EARLY_HINTS.
     * <p>
     * Este código de estado está pensado principalmente para ser usado con el encabezado Link,
     * permitiendo que el agente de usuario empiece a pre-cargar recursos mientras el servidor prepara una respuesta.
     */
    int EARLY_HINTS = 103;

    /**
     * The constant OK.
     * <p>
     * La solicitud ha tenido éxito.
     */
    int OK = 200;

    /**
     * The constant CREATED.
     * <p>
     * La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello.
     * Ésta es típicamente la respuesta enviada después de una petición PUT.
     */
    int CREATED = 201;

    /**
     * The constant ACCEPTED.
     * <p>
     * La solicitud se ha recibido, pero aún no se ha actuado. Es una petición "sin compromiso",
     * lo que significa que no hay manera en HTTP que permite enviar una respuesta asíncrona que indique
     * el resultado del procesamiento de la solicitud.
     */
    int ACCEPTED = 202;

    /**
     * The constant NON_AUTHORITATIVE_INFORMATION.
     * <p>
     * La petición se ha completado con éxito, pero su contenido no se ha obtenido de la fuente originalmente
     * solicitada, sino que se recoge de una copia local o de un tercero. Excepto esta condición,
     * se debe preferir una respuesta de 200 OK en lugar de esta respuesta.
     */
    int NON_AUTHORITATIVE_INFORMATION = 203;

    /**
     * The constant NO_CONTENT.
     * <p>
     * La petición se ha completado con éxito pero su respuesta no tiene ningún contenido,
     * aunque los encabezados pueden ser útiles. El agente de usuario puede actualizar
     * sus encabezados en caché para este recurso con los nuevos valores.
     */
    int NO_CONTENT = 204;

    /**
     * The constant RESET_CONTENT.
     * <p>
     * La petición se ha completado con éxito, pero su respuesta no tiene contenidos y además,
     * el agente de usuario tiene que inicializar la página desde la que se realizó la petición,
     * este código es útil por ejemplo para páginas con formularios cuyo contenido debe borrarse
     * después de que el usuario lo envíe.
     */
    int RESET_CONTENT = 205;

    /**
     * The constant PARTIAL_CONTENT.
     * <p>
     * La petición servirá parcialmente el contenido solicitado.
     * Esta característica es utilizada por herramientas de descarga
     * como wget para continuar la transferencia de descargas anteriormente
     * interrumpidas, o para dividir una descarga y procesar las partes simultáneamente.
     */
    int PARTIAL_CONTENT = 206;

    /**
     * The constant MULTI_STATUS.
     * <p>
     * Una respuesta Multi-Estado transmite información sobre varios recursos en situaciones en las que varios códigos
     * de estado podrían ser apropiados. El cuerpo de la petición es un mensaje XML.
     */
    int MULTI_STATUS = 207;

    /**
     * The constant IM_USED.
     * <p>
     * El servidor ha cumplido una petición GET para el recurso y la respuesta es una representación del resultado
     * de una o más manipulaciones de instancia aplicadas a la instancia actual.
     */
    int IM_USED = 226;

    /**
     * The constant MULTIPLE_CHOICE.
     * <p>
     * Esta solicitud tiene más de una posible respuesta. SystemUser-Agent o el usuario debe escoger uno de ellos.
     * No hay forma estandarizada de seleccionar una de las respuestas.
     */
    int MULTIPLE_CHOICE = 300;

    /**
     * The constant MOVED_PERMANENTLY.
     * <p>
     * Este código de respuesta significa que la URI  del recurso solicitado ha sido cambiado.
     * Probablemente una nueva URI sea devuelta en la respuesta.
     */
    int MOVED_PERMANENTLY = 301;

    /**
     * The constant FOUND.
     * <p>
     * Este código de respuesta significa que el recurso de la URI solicitada ha sido cambiado temporalmente.
     * Nuevos cambios en la URI serán agregados en el futuro. Por lo tanto,
     * la misma URI debe ser usada por el cliente en futuras solicitudes.
     */
    int FOUND = 302;

    /**
     * The constant SEE_OTHER.
     * <p>
     * El servidor envía esta respuesta para dirigir al cliente a un nuevo recurso solicitado a
     * otra dirección usando una petición GET.
     */
    int SEE_OTHER = 303;

    /**
     * The constant NOT_MODIFIED.
     * <p>
     * Esta es usada para propósitos de "caché". Le indica al cliente que la respuesta no ha sido modificada.
     * Entonces, el cliente puede continuar usando la misma versión almacenada en su caché.
     */
    int NOT_MODIFIED = 304;

    /**
     * The constant TEMPORARY_REDIRECT.
     * <p>
     * El servidor envía esta respuesta para dirigir al cliente a obtener el recurso solicitado
     * a otra URI con el mismo método que se usó la petición anterior. Tiene la misma semántica
     * que el código de respuesta HTTP 302 Found, con la excepción de que el agente usuario
     * no debe cambiar el método HTTP usado: si un POST fue usado en la primera petición,
     * otro POST debe ser usado en la segunda petición.
     */
    int TEMPORARY_REDIRECT = 307;

    /**
     * The constant PERMANENT_REDIRECT.
     * <p>
     * Significa que el recurso ahora se encuentra permanentemente en otra URI,
     * especificada por la respuesta de encabezado HTTP Location:.
     * Tiene la misma semántica que el código de respuesta HTTP 301 Moved Permanently,
     * con la excepción de que el agente usuario no debe cambiar el método HTTP usado:
     * si un POST fue usado en la primera petición, otro POST debe ser usado en la segunda petición.
     */
    int PERMANENT_REDIRECT = 308;

    /**
     * The constant BAD_REQUEST.
     * <p>
     * Esta respuesta significa que el servidor no pudo interpretar la solicitud dada una sintaxis inválida.
     */
    int BAD_REQUEST = 400;

    /**
     * The constant UNAUTHORIZED.
     * <p>
     * Es necesario autenticar para obtener la respuesta solicitada.
     * Esta es similar a 403, pero en este caso, la autenticación es posible.
     */
    int UNAUTHORIZED = 401;

    /**
     * The constant FORBIDDEN.
     * <p>
     * El cliente no posee los permisos necesarios para cierto contenido,
     * por lo que el servidor está rechazando otorgar una respuesta apropiada.
     */
    int FORBIDDEN = 403;

    /**
     * The constant NOT_FOUND.
     * <p>
     * El servidor no pudo encontrar el contenido solicitado.
     * Este código de respuesta es uno de los más famosos dada su alta ocurrencia en la web.
     */
    int NOT_FOUND = 404;

    /**
     * The constant METHOD_NOT_ALLOWED.
     * <p>
     * El método solicitado es conocido por el servidor pero ha sido deshabilitado y no puede ser utilizado.
     * Los dos métodos obligatorios, GET y HEAD, nunca deben ser deshabilitados y
     * no deberían retornar este código de error.
     */
    int METHOD_NOT_ALLOWED = 405;

    /**
     * The constant NOT_ACCEPTABLE.
     * <p>
     * Esta respuesta es enviada cuando el servidor, después de aplicar una negociación de contenido
     * servidor-impulsado, no encuentra ningún contenido seguido por la criteria dada por el usuario.
     */
    int NOT_ACCEPTABLE = 406;

    /**
     * The constant PROXY_AUTHENTICATION_REQUIRED.
     * <p>
     * Esto es similar al código 401, pero la autenticación debe estar hecha a partir de un proxy.
     */
    int PROXY_AUTHENTICATION_REQUIRED = 407;

    /**
     * The constant REQUEST_TIME_OUT.
     * <p>
     * Esta respuesta es enviada en una conexión inactiva en algunos servidores,
     * incluso sin alguna petición previa por el cliente. Significa que el servidor
     * quiere desconectar esta conexión sin usar. Esta respuesta es muy usada desde
     * algunos navegadores, como Chrome, Firefox 27+, o IE9, usa mecanismos de
     * pre-conexión HTTP para acelerar la navegación. También hay que tener en cuenta
     * que algunos servidores simplemente desconecta la conexión sin enviar este mensaje.
     */
    int REQUEST_TIME_OUT = 408;

    /**
     * The constant CONFLICT.
     * <p>
     * Esta respuesta puede ser enviada cuando una petición tiene conflicto con el estado actual del servidor.
     */
    int CONFLICT = 409;

    /**
     * The constant GONE.
     * <p>
     * Esta respuesta puede ser enviada cuando el contenido solicitado ha sido borrado del servidor.
     */
    int GONE = 410;

    /**
     * The constant LENGTH_REQUIRED.
     * <p>
     * El servidor rechaza la petición porque el campo de encabezado Content-Length
     * no esta definido y el servidor lo requiere.
     */
    int LENGTH_REQUIRED = 411;

    /**
     * The constant PRECONDITION_FAILED.
     * <p>
     * El cliente ha indicado pre-condiciones en sus encabezados la cual el servidor no cumple.
     */
    int PRECONDITION_FAILED = 412;

    /**
     * The constant PAYLOAD_TOO_LARGE.
     * <p>
     * La entidad de petición es más larga que los límites definidos por el servidor;
     * el servidor puede cerrar la conexión o retornar un campo de encabezado Retry-After.
     */
    int PAYLOAD_TOO_LARGE = 413;

    /**
     * The constant URI_TOO_LONG.
     * <p>
     * La URI solicitada por el cliente es más larga de lo que el servidor está dispuesto a interpretar.
     */
    int URI_TOO_LONG = 414;

    /**
     * The constant UNSUPPORTED_MEDIA_TYPE.
     * <p>
     * El formato multimedia de los datos solicitados no está soportado por el servidor,
     * por lo cual el servidor rechaza la solicitud.
     */
    int UNSUPPORTED_MEDIA_TYPE = 415;

    /**
     * The constant REQUESTED_RANGE_NOT_SATISFIABLE.
     * <p>
     * El rango especificado por el campo de encabezado Range en la solicitud no cumple;
     * es posible que el rango está fuera del tamaño de los datos objetivo del URI.
     */
    int REQUESTED_RANGE_NOT_SATISFIABLE = 416;

    /**
     * The constant EXPECTATION_FAILED.
     * <p>
     * Significa que la expectativa indicada por el campo de encabezado
     * Expect solicitada no puede ser cumplida por el servidor.
     */
    int EXPECTATION_FAILED = 417;

    /**
     * The constant IM_NOT_TEAPOT.
     * <p>
     * El servidor se rehúsa a intentar hacer café con una tetera.
     */
    int IM_NOT_TEAPOT = 418;

    /**
     * The constant MISDIRECTED_REQUEST.
     * <p>
     * La petición fue dirigida a un servidor que no es capaz de producir una respuesta.
     * Esto puede ser enviado por un servidor que no está configurado para producir
     * respuestas por la combinación del esquema y la autoridad que están incluidos en la URI solicitada
     */
    int MISDIRECTED_REQUEST = 421;

    /**
     * The constant UNPROCESSABLE_ENTITY.
     * <p>
     * La petición estaba bien formada pero no se pudo seguir debido a errores de semántica.
     */
    int UNPROCESSABLE_ENTITY = 422;

    /**
     * The constant LOCKED.
     * <p>
     * El recurso que está siendo accedido está bloqueado.
     */
    int LOCKED = 423;

    /**
     * The constant FAILED_DEPENDENCY.
     * <p>
     * La petición falló debido a una falla de una petición previa.
     */
    int FAILED_DEPENDENCY = 424;

    /**
     * The constant UPGRADE_REQUIRED.
     * <p>
     * El servidor se rehúsa a aplicar la solicitud usando el protocolo actual
     * pero puede estar dispuesto a hacerlo después que el cliente se actualice
     * a un protocolo diferente. El servidor envía un encabezado Upgrade en una
     * respuesta para indicar los protocolos requeridos.
     */
    int UPGRADE_REQUIRED = 426;

    /**
     * The constant PRECONDITION_REQUIRED.
     * <p>
     * El servidor origen requiere que la solicitud sea condicional.
     * Tiene la intención de prevenir problemas de 'actualización perdida',
     * donde un cliente OBTIENE un estado del recurso, lo modifica, y lo
     * PONE devuelta al servidor, cuando mientras un tercero ha modificado
     * el estado del servidor, llevando a un conflicto.
     */
    int PRECONDITION_REQUIRED = 428;

    /**
     * The constant TOO_MANY_REQUESTS.
     * <p>
     * El usuario ha enviado demasiadas solicitudes en un periodo de tiempo dado.
     */
    int TOO_MANY_REQUESTS = 429;

    /**
     * The constant REQUEST_HEADER_FIELDS_TOO_LARGE.
     * <p>
     * El servidor no está dispuesto a procesar la solicitud porque los campos de encabezado son demasiado largos.
     * La solicitud PUEDE volver a subirse después de reducir el tamaño de los campos de encabezado solicitados.
     */
    int REQUEST_HEADER_FIELDS_TOO_LARGE = 431;

    /**
     * The constant UNAVAILABLE_FOR_LEGAL_REASONS.
     * <p>
     * El usuario solicita un recurso ilegal, como alguna página web censurada por algún gobierno.
     */
    int UNAVAILABLE_FOR_LEGAL_REASONS = 451;

    /**
     * The constant INTERNAL_SERVER_ERROR.
     * <p>
     * El servidor ha encontrado una situación que no sabe cómo manejarla.
     */
    int INTERNAL_SERVER_ERROR = 500;

    /**
     * The constant NOT_IMPLEMENTED.
     * <p>
     * El método solicitado no está soportado por el servidor y no puede ser manejado.
     * Los únicos métodos que los servidores requieren soporte
     * (y por lo tanto no deben retornar este código) son GET y HEAD.
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * The constant BAD_GATEWAY.
     * <p>
     * Esta respuesta de error significa que el servidor, mientras trabaja como una puerta
     * de enlace para obtener una respuesta necesaria para manejar la petición, obtuvo una respuesta inválida.
     */
    int BAD_GATEWAY = 502;

    /**
     * The constant SERVICE_UNAVAILABLE.
     * <p>
     * El servidor no está listo para manejar la petición. Causas comunes puede ser que el servidor
     * está caído por mantenimiento o está sobrecargado. Hay que tomar en cuenta que junto con esta
     * respuesta, una página usuario-amigable explicando el problema debe ser enviada.
     * Estas respuestas deben ser usadas para condiciones temporales y el encabezado HTTP Retry-After:
     * debería, si es posible, contener el tiempo estimado antes de la recuperación del servicio.
     * El webmaster debe también cuidar los encabezados relacionados al caché que son enviados
     * junto a esta respuesta, ya que estas respuestas de condición temporal deben usualmente no estar en el caché.
     */
    int SERVICE_UNAVAILABLE = 503;

    /**
     * The constant GATEWAY_TIME_OUT.
     * <p>
     * Esta respuesta de error es dada cuando el servidor está actuando como una puerta de enlace
     * y no puede obtener una respuesta a tiempo.
     */
    int GATEWAY_TIME_OUT = 504;

    /**
     * The constant HTTP_VERSION_NOT_SUPPORTED.
     * <p>
     * La versión de HTTP usada en la petición no está soportada por el servidor.
     */
    int HTTP_VERSION_NOT_SUPPORTED = 505;

    /**
     * The constant VARIANT_ALSO_NEGOTIATES.
     * <p>
     * El servidor tiene un error de configuración interna: negociación de contenido
     * transparente para la petición resulta en una referencia circular.
     */
    int VARIANT_ALSO_NEGOTIATES = 506;

    /**
     * The constant INSIFFICIENT_STORAGE.
     * <p>
     * El servidor tiene un error de configuración interna: la variable de recurso escogida
     * está configurada para acoplar la negociación de contenido transparente misma,
     * y no es por lo tanto un punto final adecuado para el proceso de negociación.
     */
    int INSIFFICIENT_STORAGE = 507;

    /**
     * The constant LOOP_DETECTED.
     * <p>
     * El servidor detectó un ciclo infinito mientras procesaba la solicitud.
     */
    int LOOP_DETECTED = 508;

    /**
     * The constant NOT_EXTENDED.
     * <p>
     * Extensiones adicionales para la solicitud son requeridas para que el servidor las cumpla.
     */
    int NOT_EXTENDED = 510;

    /**
     * The constant NETWORK_AUTHENTICATION_REQUIRED.
     * <p>
     * El código de estado 511 indica que el cliente necesita autenticar para obtener acceso a la red.
     */
    int NETWORK_AUTHENTICATION_REQUIRED = 511;

    static boolean isInformative(int status) {
        return status >= 100 && status < 200;
    }

    static boolean isCorrect(int status) {
        return status >= 200 && status < 300;
    }

    static boolean isRedirect(int status) {
        return status >= 300 && status < 400;
    }

    static boolean isClientError(int status) {
        return status >= 400 && status < 500;
    }

    static boolean isServerError(int status) {
        return status >= 500 && status < 600;
    }

}
