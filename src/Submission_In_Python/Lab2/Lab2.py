"""
    Name: ATHAR NOOR MOHAMMAD RAFEE
    ID: 20101396
    SECTION: 9
    CSE422 LAB ASSIGNMENT 02 FALL 2022
"""

import random
import sys
from typing import Final

"""
    Please use Python 3.10.8 or higher
"""

MIN_VALUE: Final[int] = -sys.maxsize - 1
MAX_VALUE: Final[int] = sys.maxsize


def alpha_beta_pruning(cur_depth: int, node_index: int, is_maximizing_player: bool,
                       points: list[int], alpha: int, beta: int) -> int:
    if cur_depth == 3:
        return points[node_index]

    if is_maximizing_player:
        max_points = MIN_VALUE
        for i in range(0, 2):
            cur_value = alpha_beta_pruning(cur_depth + 1, node_index * 2 + i, False, points, alpha, beta)
            max_points = max(max_points, cur_value)
            alpha = max(alpha, max_points)

            if beta <= alpha:
                break

        return max_points

    else:
        min_points = MAX_VALUE

        for i in range(0, 2):
            cur_value = alpha_beta_pruning(cur_depth + 1, node_index * 2 + i, True, points, alpha, beta)
            min_points = min(min_points, cur_value)
            beta = min(beta, min_points)

            if beta <= alpha:
                break

        return min_points


limits: list[int] = []
list_of_points_values_after_shuffle: list[int] = []
min_limit: int | None = None
max_limit: int | None = None
total_points_to_win: int | None = None
total_number_of_shaffle: int | None = None


def initialize() -> None:
    take_input_and_set_values()
    create_random_list_of_points()


def take_input_and_set_values() -> None:
    student_id = input("Enter your student ID\n").replace("0", "8")
    if len(student_id) != 8:
        raise Exception("Student id must need to be of length 8")
    global min_limit, max_limit, total_number_of_shaffle, total_points_to_win

    min_limit = int(student_id[4])
    max_limit = round(int(student_id[6:][::-1]) * 1.5)
    total_points_to_win = int(student_id[6:][::-1])
    total_number_of_shaffle = int(student_id[3])


def create_random_list_of_points() -> None:
    for i in range(8):
        limits.append(random.randint(min_limit, max_limit))


def shuffle_list() -> None:
    random.shuffle(limits)


def shuffle() -> None:
    for i in range(total_number_of_shaffle):
        shuffle_list()
        list_of_points_values_after_shuffle.append(alpha_beta_pruning(0, 0, True,
                                                                      limits, MIN_VALUE, MAX_VALUE))


def get_stat() -> tuple[int, int]:
    win_count: int = 0
    max_point = MIN_VALUE
    for i in list_of_points_values_after_shuffle:
        if i >= total_points_to_win:
            win_count += 1
        max_point = max(max_point, i)
    return win_count, max_point


initialize()
# Task1
achieved_points = alpha_beta_pruning(0, 0, True, limits, MIN_VALUE, MAX_VALUE)
print("Generated 8 random points between the minimum and maximum point")
print(f"limits: {limits}")
print(f"Total points to win: {total_points_to_win}")
print(f"Achieved point by applying alpha-beta pruning = {achieved_points}")
print("The Winner is Megatron" if achieved_points < total_points_to_win else "The winner is Optimus Prime")
# Task2 shuffle
shuffle()
print("\nAfter shuffle:")
print(f"List of all points values from each shuffle: {list_of_points_values_after_shuffle}")
total_win_count, maximum_value = get_stat()
print(f"The maximum value of all shuffles: {maximum_value}")
print(f"Won {total_win_count} times out of {total_number_of_shaffle} number of shuffles")
