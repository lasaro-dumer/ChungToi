
package chungtoi.client.proxy;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the chungtoi.client.proxy package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _EndMatchResponse_QNAME = new QName("http://server.chungtoi/", "endMatchResponse");
    private final static QName _HaveMatch_QNAME = new QName("http://server.chungtoi/", "haveMatch");
    private final static QName _EndMatch_QNAME = new QName("http://server.chungtoi/", "endMatch");
    private final static QName _GetOpponent_QNAME = new QName("http://server.chungtoi/", "getOpponent");
    private final static QName _HaveMatchResponse_QNAME = new QName("http://server.chungtoi/", "haveMatchResponse");
    private final static QName _GetOpponentResponse_QNAME = new QName("http://server.chungtoi/", "getOpponentResponse");
    private final static QName _PlacePieceResponse_QNAME = new QName("http://server.chungtoi/", "placePieceResponse");
    private final static QName _IsMyTurnResponse_QNAME = new QName("http://server.chungtoi/", "isMyTurnResponse");
    private final static QName _PlayerSignup_QNAME = new QName("http://server.chungtoi/", "playerSignup");
    private final static QName _IsMyTurn_QNAME = new QName("http://server.chungtoi/", "isMyTurn");
    private final static QName _MovePiece_QNAME = new QName("http://server.chungtoi/", "movePiece");
    private final static QName _PlacePiece_QNAME = new QName("http://server.chungtoi/", "placePiece");
    private final static QName _MovePieceResponse_QNAME = new QName("http://server.chungtoi/", "movePieceResponse");
    private final static QName _GetBoard_QNAME = new QName("http://server.chungtoi/", "getBoard");
    private final static QName _GetBoardResponse_QNAME = new QName("http://server.chungtoi/", "getBoardResponse");
    private final static QName _PlayerSignupResponse_QNAME = new QName("http://server.chungtoi/", "playerSignupResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: chungtoi.client.proxy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PlayerSignupResponse }
     * 
     */
    public PlayerSignupResponse createPlayerSignupResponse() {
        return new PlayerSignupResponse();
    }

    /**
     * Create an instance of {@link GetBoard }
     * 
     */
    public GetBoard createGetBoard() {
        return new GetBoard();
    }

    /**
     * Create an instance of {@link GetBoardResponse }
     * 
     */
    public GetBoardResponse createGetBoardResponse() {
        return new GetBoardResponse();
    }

    /**
     * Create an instance of {@link MovePieceResponse }
     * 
     */
    public MovePieceResponse createMovePieceResponse() {
        return new MovePieceResponse();
    }

    /**
     * Create an instance of {@link MovePiece }
     * 
     */
    public MovePiece createMovePiece() {
        return new MovePiece();
    }

    /**
     * Create an instance of {@link PlacePiece }
     * 
     */
    public PlacePiece createPlacePiece() {
        return new PlacePiece();
    }

    /**
     * Create an instance of {@link IsMyTurn }
     * 
     */
    public IsMyTurn createIsMyTurn() {
        return new IsMyTurn();
    }

    /**
     * Create an instance of {@link PlayerSignup }
     * 
     */
    public PlayerSignup createPlayerSignup() {
        return new PlayerSignup();
    }

    /**
     * Create an instance of {@link IsMyTurnResponse }
     * 
     */
    public IsMyTurnResponse createIsMyTurnResponse() {
        return new IsMyTurnResponse();
    }

    /**
     * Create an instance of {@link GetOpponentResponse }
     * 
     */
    public GetOpponentResponse createGetOpponentResponse() {
        return new GetOpponentResponse();
    }

    /**
     * Create an instance of {@link PlacePieceResponse }
     * 
     */
    public PlacePieceResponse createPlacePieceResponse() {
        return new PlacePieceResponse();
    }

    /**
     * Create an instance of {@link GetOpponent }
     * 
     */
    public GetOpponent createGetOpponent() {
        return new GetOpponent();
    }

    /**
     * Create an instance of {@link HaveMatchResponse }
     * 
     */
    public HaveMatchResponse createHaveMatchResponse() {
        return new HaveMatchResponse();
    }

    /**
     * Create an instance of {@link EndMatch }
     * 
     */
    public EndMatch createEndMatch() {
        return new EndMatch();
    }

    /**
     * Create an instance of {@link EndMatchResponse }
     * 
     */
    public EndMatchResponse createEndMatchResponse() {
        return new EndMatchResponse();
    }

    /**
     * Create an instance of {@link HaveMatch }
     * 
     */
    public HaveMatch createHaveMatch() {
        return new HaveMatch();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndMatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "endMatchResponse")
    public JAXBElement<EndMatchResponse> createEndMatchResponse(EndMatchResponse value) {
        return new JAXBElement<EndMatchResponse>(_EndMatchResponse_QNAME, EndMatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HaveMatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "haveMatch")
    public JAXBElement<HaveMatch> createHaveMatch(HaveMatch value) {
        return new JAXBElement<HaveMatch>(_HaveMatch_QNAME, HaveMatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EndMatch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "endMatch")
    public JAXBElement<EndMatch> createEndMatch(EndMatch value) {
        return new JAXBElement<EndMatch>(_EndMatch_QNAME, EndMatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOpponent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "getOpponent")
    public JAXBElement<GetOpponent> createGetOpponent(GetOpponent value) {
        return new JAXBElement<GetOpponent>(_GetOpponent_QNAME, GetOpponent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HaveMatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "haveMatchResponse")
    public JAXBElement<HaveMatchResponse> createHaveMatchResponse(HaveMatchResponse value) {
        return new JAXBElement<HaveMatchResponse>(_HaveMatchResponse_QNAME, HaveMatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOpponentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "getOpponentResponse")
    public JAXBElement<GetOpponentResponse> createGetOpponentResponse(GetOpponentResponse value) {
        return new JAXBElement<GetOpponentResponse>(_GetOpponentResponse_QNAME, GetOpponentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlacePieceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "placePieceResponse")
    public JAXBElement<PlacePieceResponse> createPlacePieceResponse(PlacePieceResponse value) {
        return new JAXBElement<PlacePieceResponse>(_PlacePieceResponse_QNAME, PlacePieceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsMyTurnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "isMyTurnResponse")
    public JAXBElement<IsMyTurnResponse> createIsMyTurnResponse(IsMyTurnResponse value) {
        return new JAXBElement<IsMyTurnResponse>(_IsMyTurnResponse_QNAME, IsMyTurnResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlayerSignup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "playerSignup")
    public JAXBElement<PlayerSignup> createPlayerSignup(PlayerSignup value) {
        return new JAXBElement<PlayerSignup>(_PlayerSignup_QNAME, PlayerSignup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsMyTurn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "isMyTurn")
    public JAXBElement<IsMyTurn> createIsMyTurn(IsMyTurn value) {
        return new JAXBElement<IsMyTurn>(_IsMyTurn_QNAME, IsMyTurn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovePiece }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "movePiece")
    public JAXBElement<MovePiece> createMovePiece(MovePiece value) {
        return new JAXBElement<MovePiece>(_MovePiece_QNAME, MovePiece.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlacePiece }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "placePiece")
    public JAXBElement<PlacePiece> createPlacePiece(PlacePiece value) {
        return new JAXBElement<PlacePiece>(_PlacePiece_QNAME, PlacePiece.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovePieceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "movePieceResponse")
    public JAXBElement<MovePieceResponse> createMovePieceResponse(MovePieceResponse value) {
        return new JAXBElement<MovePieceResponse>(_MovePieceResponse_QNAME, MovePieceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBoard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "getBoard")
    public JAXBElement<GetBoard> createGetBoard(GetBoard value) {
        return new JAXBElement<GetBoard>(_GetBoard_QNAME, GetBoard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBoardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "getBoardResponse")
    public JAXBElement<GetBoardResponse> createGetBoardResponse(GetBoardResponse value) {
        return new JAXBElement<GetBoardResponse>(_GetBoardResponse_QNAME, GetBoardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlayerSignupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.chungtoi/", name = "playerSignupResponse")
    public JAXBElement<PlayerSignupResponse> createPlayerSignupResponse(PlayerSignupResponse value) {
        return new JAXBElement<PlayerSignupResponse>(_PlayerSignupResponse_QNAME, PlayerSignupResponse.class, null, value);
    }

}
