# UNC Charlotte
# ITCS 5153 - Applied AI - Spring 2025
# Lab 3
# Adversarial Search / Game Playing
# This module implements minimax search and alpha-beta pruning
# Student ID: 801289595
# Created the connect four game and functionality using Keith Galli connect four tutorial made modifications to show understanding
# I did not create this all on my own and do not claim to I followed the tutorial and made adjustments for the assignment
import random
import sys
import math
import pygame
import numpy as np
import time
from scipy.ndimage import watershed_ift

#List of global variables
ROW_COUNT = 6
COLUMN_COUNT = 7
RED = (255, 0, 0)
BLACK = (0,0,0)
YELLOW = (255, 255, 0)
BLUE = (0,0,255)

PLAYER = 0
AI = 1

EMPTY = 0
PLAYER_PIECE = 1
AI_PIECE = 2

WINDOW_LENGTH = 4

MINIMAXMODE = 0
ALPHABETAMODE = 1

nodes_visited = 0
log = []
end_screen_choice = ["Press 'n' - New Game", "Press 'r' - Restart Game", "Press 'q' - Quit Game"]
game_over = False

#instantiation of a 6x7 board
def create_board():
    board = np.zeros((ROW_COUNT,COLUMN_COUNT))
    return board


#method that drops specific players piece
def drop_piece(board, row, col, piece):
    board[row][col] = piece

#method that checks to see if top row is taken
def is_valid_location(board, col):
    return board[ROW_COUNT - 1][col] == 0

#method that to see next available row
def get_next_open_row(board, col):
    for r in range(ROW_COUNT):
        if board[r][col] == 0:
            return r

#displays board flips the numpy array
def print_board(board):
    print(np.flip(board, 0))

#checks all of the available the moves on the board to see if a player won
def winning_move(board, piece):

    #Check horizontal location
    for c in range(COLUMN_COUNT - 3):
        for r in range(ROW_COUNT):
            if board[r][c] == piece and board[r][c+1] == piece and board[r][c+2] == piece and board[r][c+3] == piece:
                return True

    #check vertical location
    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT - 3):
            if board[r][c] == piece and board[r+1][c] == piece and board[r+2][c] == piece and board[r+3][c] == piece:
                return True

    #check positive slope diagonal
    for c in range(COLUMN_COUNT - 3):
        for r in range(ROW_COUNT - 3):
            if board[r][c] == piece and board[r+1][c+1] == piece and board[r+2][c+2] == piece and board[r+3][c+3] == piece:
                return True

    #Check negative diagonal
    for c in range(COLUMN_COUNT - 3):
        for r in range(ROW_COUNT - 1, 2, -1):
            if board[r][c] == piece and board[r-1][c+1] == piece and board[r-2][c+2] == piece and board[r-3][c+3] == piece:
                return True

#method to get score for given window. Window can be horizontal, vertical, positive diagonal, negative diagonal
def evaluate_window(window, piece):
    score = 0
    opp_piece = PLAYER_PIECE
    if piece == PLAYER_PIECE:
        opp_piece = AI_PIECE

    #score for winning move
    if window.count(piece) == 4:
        score += 1000

    #score for connections of 3
    elif window.count(piece) == 3 and window.count(EMPTY) == 1:
        score += 5

    #score for connections of 2
    elif window.count(piece) == 2 and window.count(EMPTY) == 2:
        score += 2

    #score for opponents winning move
    if window.count(opp_piece) == 3 and window.count(EMPTY) == 1:
        score -= 4

    return score

#method to check if a move is a terminal node. For AI winning or Player winning or no valid moves being available, ie: board is full
def is_terminal_node(board):
    return winning_move(board, PLAYER_PIECE) or winning_move(board, AI_PIECE) or len(get_valid_locations(board)) == 0

#minimax algorithm to maximizing AI and minimizing player
def minimax(board, depth, maximizingPlayer):
    global nodes_visited

    nodes_visited +=1

    valid_locations = get_valid_locations(board)
    is_terminal = is_terminal_node(board)
    if depth == 0 or is_terminal:
        if is_terminal:
            if winning_move(board, AI_PIECE):
                return (None, 100000)
            elif winning_move(board, PLAYER_PIECE):
                return (None, -100000)
            else:
                return (None, 0)
        else:
            return (None, score_position(board, AI_PIECE))
    if maximizingPlayer:
        value = -math.inf
        column = random.choice(valid_locations)
        for col in valid_locations:
            row = get_next_open_row(board, col)
            board_copy = board.copy()
            drop_piece(board_copy, row, col, AI_PIECE)
            new_score = minimax(board_copy, depth -1, False)[1]
            if new_score > value:
                value = new_score
                column = col
        return column, value
    else:
        value = math.inf
        column = random.choice(valid_locations)
        for col in valid_locations:
            row = get_next_open_row(board, col)
            board_copy = board.copy()
            drop_piece(board_copy, row, col, PLAYER_PIECE)
            new_score = minimax(board_copy, depth -1, True)[1]
            if new_score < value:
                value = new_score
                column = col
        return column, value

#alpha beta method same as minimax but prunes off nodes that don't maximize or minimize (Less nodes being visited). Decreases run time.
def alpha_beta(board, depth, alpha, beta, maximizingPlayer):
    global nodes_visited

    nodes_visited += 1
    valid_locations = get_valid_locations(board)
    is_terminal = is_terminal_node(board)
    if depth == 0 or is_terminal:
        if is_terminal:
            if winning_move(board, AI_PIECE):
                return (None, 100000)
            elif winning_move(board, PLAYER_PIECE):
                return (None, -100000)
            else:
                return (None, 0)
        else:
            return (None, score_position(board, AI_PIECE))
    if maximizingPlayer:
        value = -math.inf
        column = random.choice(valid_locations)
        for col in valid_locations:
            row = get_next_open_row(board, col)
            board_copy = board.copy()
            drop_piece(board_copy, row, col, AI_PIECE)
            new_score = alpha_beta(board_copy, depth -1, alpha, beta, False)[1]
            if new_score > value:
                value = new_score
                column = col
            alpha = max(alpha, value)
            if alpha >= beta:
                break
        return column, value
    else:
        value = math.inf
        column = random.choice(valid_locations)
        for col in valid_locations:
            row = get_next_open_row(board, col)
            board_copy = board.copy()
            drop_piece(board_copy, row, col, PLAYER_PIECE)
            new_score = alpha_beta(board_copy, depth -1, alpha, beta, True)[1]
            if new_score < value:
                value = new_score
                column = col
            beta = min(beta, value)
            if alpha >= beta:
                break
        return column, value


#method that gives score for the piece dropped
def score_position(board, piece):
    score = 0

    #score center
    center_array = [int(i) for i in list(board[:, COLUMN_COUNT//2])]
    center_count = center_array.count(piece)
    score += 3

    #horizontal score
    for r in range(ROW_COUNT):
        row_array = [int(i) for i in list(board[r, :])]
        for c in range(COLUMN_COUNT - 3):
            window = row_array[c:c + WINDOW_LENGTH]
            score += evaluate_window(window, piece)

    #verticle score
    for c in range(COLUMN_COUNT):
        col_array = [int(i) for i in list(board[:, c])]
        for r in range(ROW_COUNT - 3):
            window = col_array[r: r+WINDOW_LENGTH]
            score += evaluate_window(window, piece)

    #positive diagonal score
    for r in range(ROW_COUNT - 3):
        for c in range(COLUMN_COUNT - 3):
            window = [board[r+i][c+i] for i in range(WINDOW_LENGTH)]
            score += evaluate_window(window, piece)

    #negative diagonal score
    for r in range(ROW_COUNT - 3):
        for c in range(COLUMN_COUNT - 3):
            window = [board[r+3 - i][c + i] for i in range(WINDOW_LENGTH)]
            score += evaluate_window(window, piece)

    return score

#creates list of locations that a piece can be dropped
def get_valid_locations(board):
    valid_locations = []
    for col in range(COLUMN_COUNT):
        if is_valid_location(board, col):
            valid_locations.append(col)
    return valid_locations

#method that iterates through all valid moves and picks move that has highest score
def pick_best_move(board, piece):
    best_score = -1000
    valid_locations = get_valid_locations(board)
    best_col = random.choice(valid_locations)
    for col in valid_locations:
        row = get_next_open_row(board, col)
        temp_board = board.copy()
        drop_piece(temp_board, row, col, piece)
        score = score_position(temp_board, piece)
        if score > best_score:
            best_score = score
            best_col = col

    return best_col





#method that draws the connect 4 board
def draw_board(board):
    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            pygame.draw.rect(screen, RED, (c*SQUARE_SIZE, r*SQUARE_SIZE+SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE))
            pygame.draw.rect(screen, BLACK, (c * SQUARE_SIZE + 2.5, r * SQUARE_SIZE + SQUARE_SIZE + 2.5, SQUARE_SIZE - 5, SQUARE_SIZE - 5))

    for c in range(COLUMN_COUNT):
        for r in range(ROW_COUNT):
            if board[r][c] == PLAYER_PIECE:
                pygame.draw.rect(screen, YELLOW, (c * SQUARE_SIZE + 2.5, height - r * SQUARE_SIZE - SQUARE_SIZE + 2.5, SQUARE_SIZE - 5, SQUARE_SIZE - 5))
            elif board[r][c] == AI_PIECE:
                pygame.draw.rect(screen, BLUE, (c * SQUARE_SIZE + 2.5, height - r * SQUARE_SIZE - SQUARE_SIZE + 2.5, SQUARE_SIZE - 5, SQUARE_SIZE - 5))




    pygame.display.update()



board = create_board()
print(board)
running = True
turn = random.randint(PLAYER, AI)

pygame.init()

SQUARE_SIZE = 60

STARTSCREEN = True
ENDSCREEN = False

width = COLUMN_COUNT * SQUARE_SIZE + 350
height = (ROW_COUNT + 1) * SQUARE_SIZE

mode = 0

size = (width, height)

screen = pygame.display.set_mode(size)

myfont = pygame.font.SysFont("monospace", 30)
modefont = pygame.font.SysFont("monospace", 20)


title = myfont.render("Connect Four!", 1, RED)
minimax_choice = myfont.render("Press 'm' for minimax AI",1, RED)
alpha_beta_choice = myfont.render("Press 'a' for alpha-beta AI", 1, RED)
screen.blit(title, (240,150))
screen.blit(minimax_choice, (130, 180))
screen.blit(alpha_beta_choice, (130, 210))
pygame.display.update()




while running:
    if not STARTSCREEN:
        if turn == PLAYER:
            pygame.draw.rect(screen, BLACK, (430, 60, 275, 35))
            turn_display = myfont.render("Player's Turn", 1, YELLOW)
            screen.blit(turn_display, (430,60))
            pygame.display.update()

        if mode == MINIMAXMODE:
            mode_display = modefont.render("Algorithm used: minimax", 1, RED)
            screen.blit(mode_display, (430, 95))
        elif mode == ALPHABETAMODE:
            mode_display = modefont.render("Algorithm used: alpha-beta", 1, RED)
            screen.blit(mode_display, (430, 95))

        time_display = modefont.render("Time Elapsed:", 1, RED)
        screen.blit(time_display, (430, 120))

        log_title = modefont.render("Game Log", 1, RED)
        screen.blit(log_title, (540, 150))








    for event in pygame.event.get():

        #event to quit game
        if event.type == pygame.QUIT:
            sys.exit()

        #event for all different keydown
        if event.type == pygame.KEYDOWN:

            while STARTSCREEN:
                if event.key == pygame.K_m:
                    STARTSCREEN = False
                    mode = MINIMAXMODE
                    screen.fill((0,0,0))
                    draw_board(board)
                    pygame.display.update()
                elif event.key == pygame.K_a:
                    mode = ALPHABETAMODE
                    STARTSCREEN = False
                    screen.fill((0,0,0))
                    draw_board(board)
                    pygame.display.update()

            while ENDSCREEN:
                if event.key == pygame.K_n:
                    pass
                elif event.key == pygame.K_r:
                    pass
                elif event.key == pygame.K_q:
                    pass


        #event to show where placing piece
        if not STARTSCREEN:
            if event.type == pygame.MOUSEMOTION:
                posx = event.pos[0]
                if posx > SQUARE_SIZE * 7:
                    posx = SQUARE_SIZE * 6
                col = int(math.floor(posx / SQUARE_SIZE))
                pygame.draw.rect(screen, BLACK, (0, 0, width, SQUARE_SIZE))

                if turn == 0:
                    pygame.draw.rect(screen, YELLOW, (col * SQUARE_SIZE + 2.5, 2.5,SQUARE_SIZE - 5, SQUARE_SIZE - 5))
                if turn == 1:
                    pygame.draw.rect(screen, BLUE, (col * SQUARE_SIZE + 2.5, 2.5,SQUARE_SIZE - 5, SQUARE_SIZE - 5))
                pygame.display.update()


        #event to place piece
            if not STARTSCREEN:
                if event.type == pygame.MOUSEBUTTONDOWN:
                    if turn == PLAYER:
                        posx = event.pos[0]
                        if posx > SQUARE_SIZE * 7:
                            posx = SQUARE_SIZE * 6
                        col = int(math.floor(posx/SQUARE_SIZE))

                        if is_valid_location(board, col):
                            row = get_next_open_row(board, col)
                            drop_piece(board, row, col, PLAYER_PIECE)

                            if winning_move(board, PLAYER_PIECE):
                                game_over = True

                        print_board(board)
                        draw_board(board)
                        pygame.draw.rect(screen, BLACK, (430, 175, 330, 240))
                        log.append("P1 - Col: " + str(col + 1) + " - " + "States: N/A")

                        for index, i in enumerate(reversed(log[-10:])):
                            log_string = modefont.render(i, 1, RED)
                            screen.blit(log_string, (435, 390 - (index * 20)))

                        turn += 1
                        turn = turn % 2

    if not STARTSCREEN:
        if turn == AI and running:

            pygame.draw.rect(screen, BLACK, (430, 60, 275, 35))
            turn_display = myfont.render("Computer's Turn", 1, BLUE)
            screen.blit(turn_display, (430, 60))
            pygame.display.update()


            start_time = time.time()


            nodes_visited = 0

            if mode == MINIMAXMODE:
                col, minimax_score = minimax(board, 5, True)
            elif mode == ALPHABETAMODE:
                col, alpha_beta_score = alpha_beta(board, 5, -math.inf, math.inf, True)




            if is_valid_location(board, col):
                row = get_next_open_row(board, col)
                drop_piece(board, row, col, AI_PIECE)

                if winning_move(board, AI_PIECE):
                    game_over = True

                print_board(board)
                draw_board(board)
                elapsed_time = time.time() - start_time
                formated_elapsed_time = f"{elapsed_time:.10f}"
                time_number = modefont.render(str(formated_elapsed_time), 1, RED)
                pygame.draw.rect(screen, BLACK, (600, 120, 160, 20))
                screen.blit(time_number, (600, 120))
                pygame.draw.rect(screen, BLACK, (430, 175, 330, 240))
                log.append("AI - Col: " + str(col + 1) + " - " + "States: " + str(nodes_visited))

                for index, i in enumerate(reversed(log[-10:])):
                    log_string = modefont.render(i, 1, RED)
                    screen.blit(log_string, (435, 390 - (index * 20)))

                pygame.display.update()


                turn += 1
                turn = turn % 2

                if game_over:
                    pygame.draw.rect(screen, BLACK, (430, 5, 330, 410))
                    winning_message = myfont.render("Computer wins!", 1, BLUE)
                    screen.blit(winning_message, (260, 10))
                    for index, i in enumerate(end_screen_choice):
                        end_string = modefont.render(i, 1, RED)
                        screen.blit(end_string, (435, 50 + (index * 20)))

                    pygame.display.update()

                    pygame.time.wait(3000)
                    running = False









