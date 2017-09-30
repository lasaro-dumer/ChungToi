/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chungtoi;

/**
 *
 * @author lasaro
 */
public class Match {

    private static final char WHITE_CHAR_P = 'C';
    private static final char WHITE_CHAR_D = 'c';
    private static final char BLACK_CHAR_P = 'E';
    private static final char BLACK_CHAR_D = 'e';
    private static final int PLAYER_TIMEOUT = 60;

    private Integer whitePlayerId;
    private Integer blackPlayerId;
    private MatchState state;
    private Long lastActionTime, endedTime;
    private boolean endedByWhite, endedByBlack;
    private char[] board;

    public Match(Integer whitePlayerId, Integer blackPlayerId) {
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.state = MatchState.WHITE_TURN;
        this.lastActionTime = System.currentTimeMillis();
        this.endedByWhite = false;
        this.endedByBlack = false;
        this.endedTime = null;
        this.board = new char[9];
        for (int i = 0; i < board.length; i++) {
            board[i] = '.';
        }
    }

    public Integer getWhitePlayerId() {
        return this.whitePlayerId;
    }

    public Integer getBlackPlayerId() {
        return this.blackPlayerId;
    }

    public boolean isWhitePlayer(int userId) {
        return this.whitePlayerId == userId;
    }

    public boolean isBlackPlayer(int userId) {
        return this.blackPlayerId == userId;
    }

    public int isMyTurn(int userId) {
        this.checkTimeouts();
        if (this.isWhitePlayer(userId)) {
            return this.state.getWhiteResponse();
        } else {
            return this.state.getBlackResponse();
        }
    }

    private void checkTimeouts() {
        Long timeSpan = (this.lastActionTime - System.currentTimeMillis()) / 1000;
        if (timeSpan >= PLAYER_TIMEOUT) {
            if (this.state == MatchState.WHITE_TURN) {
                this.state = MatchState.BLACK_WIN_WO;
            } else if (this.state == MatchState.BLACK_TURN) {
                this.state = MatchState.WHITE_WIN_WO;
            }
        }
    }

    public void endMatch(int userId) {
        if (this.isActive()) {
            return;
        }
        if (this.isWhitePlayer(userId) && !this.endedByWhite) {
            this.endedByWhite = true;
        } else if (this.isBlackPlayer(userId) && !this.endedByBlack) {
            this.endedByBlack = true;
        }

        if (this.endedByBlack && this.endedByWhite) {
            this.endedTime = System.currentTimeMillis();
        }
    }

    public Long getTimeSinceEnded() {
        return this.endedTime;
    }

    public boolean isActive() {
        this.checkTimeouts();
        if (this.state.isEndState()) {
            this.endedTime = System.currentTimeMillis();
        }
        return this.endedTime == null;
    }

    public String getBoard(int userId) {
        String ret = "";
        for (int i = 0; i < board.length; i++) {
            ret += board[i];
        }
        return ret;
    }

    public int placePiece(int userId, int position, int orientation) {
        int ret = -1;
        char piece = '.';
        boolean whitePlayer = this.isWhitePlayer(userId);
        boolean blackPlayer = this.isBlackPlayer(userId);
        if (position >= 0 && position <= 8 && (this.board[position] != '.')) {
            piece = getPieceChar(whitePlayer, blackPlayer, orientation);
            if (piece != '.') {
                this.board[position] = piece;
                ret = 1;
            }
        } else {
            ret = 0;
        }
        return ret;
    }

    /*7) movePeca
Recebe: id do usuário (obtido através da chamada registraJogador),
    posição do tabuleiro onde se encontra a peça que se deseja mover (de 0 até 8, inclusive),
    sentido do deslocamento (0 a 8, inclusive), 
    número de casas deslocadas (0, 1 ou 2) e 
    orientação da peça depois da jogada (0 correspondendo a orientação perpendicular, e 1 correspondendo à orientação diagonal). 
    Para o sentido do deslocamento deve­se usar a seguinte convenção: 
        0 = diagonal esquerda­superior; 
        1 = para cima; 
        2 = diagonal direita­superior; 
        3 = esquerda; 
        4 = sem movimento; 
        5 = direita; 
        6 =diagonal esquerda­inferior; 
        7 = para baixo; 
        8 = diagonal direita­inferior.
Retorna: 
        1 (tudo certo), 
        0 (movimento inválido, por exemplo, em um sentido e deslocamento que resulta em uma posição ocupada ou fora   do   tabuleiro),  
        ­1   (parâmetros   inválidos)
     */
    public int movePiece(int userId, int currentPosition, int direction, int movement, int newOrientation) {
        int ret = -1;
        int newPosition = -1;
        boolean whitePlayer = this.isWhitePlayer(userId);
        boolean blackPlayer = this.isBlackPlayer(userId);
        if (currentPosition >= 0 && currentPosition <= 8 && (this.board[currentPosition] == '.')) {
            char piece = this.board[currentPosition];
            boolean isPieceOwner = (whitePlayer && (piece == WHITE_CHAR_D || piece == WHITE_CHAR_P))
                    || (blackPlayer && (piece == BLACK_CHAR_D || piece == BLACK_CHAR_P));
            if (isPieceOwner) {
                piece = getPieceChar(whitePlayer, blackPlayer, newOrientation);
            }
            switch(direction){
            //Para o sentido do deslocamento deve­se usar a seguinte convenção: 
                case 0://0 = diagonal esquerda­superior; 
                    break;
                case 1://1 = para cima; 
                    break;
                case 2://2 = diagonal direita­superior; 
                    break;
                case 3://3 = esquerda; 
                    break;
                case 4://4 = sem movimento; 
                    break;
                case 5://5 = direita; 
                    break;
                case 6://6 =diagonal esquerda­inferior; 
                    break;
                case 7://7 = para baixo; 
                    break;
                case 8://8 = diagonal direita­inferior.
                    break;
            }
            if (piece != '.' && newPosition > -1) {
                this.board[newPosition] = piece;
                ret = 1;
            }
        } else {
            ret = 0;
        }
        return ret;
    }

    private char getPieceChar(boolean whitePlayer, boolean blackPlayer, int orientation) {
        char piece = '.';
        if (whitePlayer && (orientation == 0)) {
            piece = WHITE_CHAR_P;
        } else if (whitePlayer && (orientation == 1)) {
            piece = WHITE_CHAR_D;
        } else if (blackPlayer && (orientation == 0)) {
            piece = BLACK_CHAR_P;
        } else if (blackPlayer && (orientation == 1)) {
            piece = BLACK_CHAR_D;
        }
        return piece;
    }
}
