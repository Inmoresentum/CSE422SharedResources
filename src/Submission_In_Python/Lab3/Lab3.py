import random
from typing import Final

'''
    Please use Python 3.10.8 or higher
'''

# Declaring some global variables
number_of_batsmen: int = 0
target_score: int = 0
list_of_batsmen: list[tuple[str, int]] = []
population: list[list[int]] = []
MAX_NUMBER_OF_ITERATION: Final[int] = int(1e5)
mutation_threshold: int = 78


def take_input() -> None:
    global number_of_batsmen, target_score
    number_of_batsmen, target_score = list(map(int, input().split()))
    for i in range(number_of_batsmen):
        cur_batsman: list[str, str] = input().split()
        list_of_batsmen.append((cur_batsman[0], int(cur_batsman[1])))


def create_initial_population(size: int) -> None:
    for i in range(size):
        population.append(list())
        for j in range(number_of_batsmen):
            # Generate A random number and check if it's greater than a certain
            # threshold which is in this case 50 or not
            # if yes, then take it else leave it.
            random_number: int = random.randint(0, 100)
            if random_number > 50:
                population[i].append(1)
            else:
                population[i].append(0)


def calculate_fitness(cur_configuration: list[int]) -> int:
    total_avg_run: int = 0
    for i in range(number_of_batsmen):
        if cur_configuration[i] == 1:
            total_avg_run += list_of_batsmen[i][1]
    if total_avg_run > target_score:
        return 0
    else:
        return int((total_avg_run / target_score) * 100)


def select_two_parent(fitness_values: list[int]) -> list[int]:
    # Need to generate two random numbers,
    # then do a battle against them and then take the winning one.
    # repeat it again for second parent as well!
    pair: list[int] = []
    i: int = 0
    while i < 2:
        first_random_parent = random.randint(0, len(population) - 1)
        second_random_parent = random.randint(0, len(population) - 1)
        if first_random_parent == second_random_parent:
            continue
        else:
            if fitness_values[first_random_parent] >= fitness_values[second_random_parent] \
                    and first_random_parent not in pair:
                i += 1
                pair.append(first_random_parent)
            elif second_random_parent not in pair:
                pair.append(second_random_parent)
                i += 1
    return pair


def cross_over(first_parent: list[int], second_parent: list[int]) -> None:
    random_point: int = random.randint(1, number_of_batsmen)
    swap_values(first_parent, random_point, second_parent)


def swap_values(first_parent: list[int], random_point: int, second_parent: list[int]) -> None:
    temp: list[int] = []
    for i in range(random_point, number_of_batsmen):
        temp.append(first_parent[i])
    for i in range(random_point, number_of_batsmen):
        first_parent[i] = second_parent[i]
    for i in range(random_point, number_of_batsmen):
        second_parent[i] = temp[i - random_point]


def mutation(child: list[int]) -> None:
    probability_of_mutation: int = random.randint(0, 100)
    if probability_of_mutation > mutation_threshold:
        random_index: int = random.randint(0, number_of_batsmen - 1)
        child[random_index] = 0 if child[random_index] == 1 else 1


def genetic_algo() -> None:
    take_input()
    initial_population_size: Final[int] = (number_of_batsmen * number_of_batsmen) + 1 if number_of_batsmen % 2 == 1 \
        else (number_of_batsmen * number_of_batsmen) + 2
    create_initial_population(initial_population_size)
    list_of_fitness_values: list[int] = []
    for i in range(len(population)):
        list_of_fitness_values.append(-1)

    break_outer_loop: bool = False
    found_solution: bool = False
    print("[", end="")
    for i in range(len(list_of_batsmen) - 1):
        print(f"'{list_of_batsmen[i][0]}',", end=" ")
    print(f"'{list_of_batsmen[number_of_batsmen - 1][0]}']")
    for i in range(MAX_NUMBER_OF_ITERATION):
        # First, we need to check the fitness values of the population
        # if any configuration meets the requirements then take it.
        # else continue with genetic algorithm
        for j in range(len(population)):
            list_of_fitness_values[j] = calculate_fitness(population[j])
            if list_of_fitness_values[j] == 100:
                print(str(population[j]).replace("[", "").replace("]", "").replace(",", ""))
                break_outer_loop = True
                found_solution = True
                break
        if break_outer_loop:
            break

        # Need to do cross over
        for j in range(int(len(population) / 2)):
            selected_parent = select_two_parent(list_of_fitness_values)
            cross_over(population[selected_parent[0]], population[selected_parent[1]])
            # Doing mutation
            mutation(population[selected_parent[0]])
            mutation(population[selected_parent[1]])

    if not found_solution:
        print(-1)


genetic_algo()
