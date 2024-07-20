#include <stdlib.h>
#include <stdio.h>
#include "cxxopts.h"
#include <mpi.h>

#define DEFAULT_SIZE "20"

typedef struct Data {
  int low;
  int high;
} Data;

void merge(float* arr, int low, int mid, int high) {

  int i, j, k;
  int num1 = mid - low + 1;
  int num2 = high - mid;

  float left[num1], right[num2];
  for (i = 0; i < num1; i++)
    left[i] = arr[low + i];
  for (j = 0; j < num2; j++)
    right[j] = arr[mid + 1 + j];

  i = 0;
  j = 0;
  k = low;

  while (i < num1 && j < num2) {

    if (left[i] <= right[j]) {
      arr[k] = left[i];
      i++;
    } else {
      arr[k] = right[j];
      j++;
    }
    k++;
  }

  while (i < num1) {
    arr[k] = left[i];
    i++;
    k++;
  }

  while (j < num2) {
    arr[k] = right[j];
    j++;
    k++;
  }
}

void* mergeSort(Data* d, float* arr) {

  int low = d->low;
  int high = d->high;

  if (low < high) {
    int mid = low + (high - low) / 2;

    Data d1 = { low, mid };
    mergeSort(&d1, arr);

    Data d2 = { mid + 1, high };
    mergeSort(&d2, arr);

    merge(arr, low, mid, high);
  }

  return 0;
}

void mergeSortMPI(float* arr, int size, int world_size, int world_rank) {

  int min_sort = size / world_size;
  int excess_sort = size % world_size;
  int startx[world_size], endx[world_size];
  int totalx[world_size];
  for (int i = 0; i < world_size; i++) {

    if (world_rank < excess_sort) {
      startx[i] = world_rank * (min_sort + 1);
      endx[i] = startx[i] + min_sort;
      totalx[i] = endx - startx + 1;
    } else {
      startx[i] = (excess_sort * (min_sort + 1)) + ((world_rank - excess_sort) * min_sort);
      endx[i] = startx[i] + min_sort - 1;
      totalx[i] = endx[i] - startx[i] + 1;
    }
  }

  Data data = { startx[world_rank], endx[world_rank] };
  mergeSort(&data, arr);

  MPI_Barrier(MPI_COMM_WORLD);

  if (world_rank == 0) {

    int sorted[world_size] = {};
    float head[world_size] = {};

    head[0] = arr[0];

    for (int i = 1; i < world_size; i++) {
      float buf;
      MPI_Recv(&buf, 1, MPI_FLOAT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      head[i] = buf;
    }

    for (int count = 0; count < size; count++) {

      float min = __FLT_MAX__;
      int from = -1;

      for (int i = 0; i < world_size; i++) {
        if (sorted[i] <= endx[i] - startx[i] && head[i] < min) {
          min = head[i];
          from = i;
        }
      }

      printf("%.05f\n", min);
      sorted[from]++;

      if (from == 0 && sorted[from] <= endx[from]) {
        head[from] = arr[sorted[from]];
      } else if (sorted[from] <= endx[from] - startx[from]) {
        float buf;
        MPI_Recv(&buf, 1, MPI_FLOAT, from, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        head[from] = buf;
      }
    }
  } else {

    float buf;
    for (int i = 0; i <= endx[world_rank] - startx[world_rank]; i++) {
      buf = arr[startx[world_rank] + i];
      MPI_Send(&buf, 1, MPI_FLOAT, 0, 0, MPI_COMM_WORLD);
    }
  }
}

int main(int argc, char* argv[]) {

  cxxopts::Options options("sort_mpi", "Sort numbers using MPI");
  options.add_options("custom", {
    {"size", "Input file size",
    cxxopts::value<int>()->default_value(DEFAULT_SIZE)} }
  );

  auto cl_options = options.parse(argc, argv);
  int sizeOfArray = cl_options["size"].as<int>();

  FILE* file = NULL;

  float array[sizeOfArray], num;
  file = fopen("input.txt", "r");
  if (file == NULL) {
    printf("Error while opening the file.\n");
    return -1;
  }

  for (int i = 0; fscanf(file, "%f", &num) != EOF; i++) {
    array[i] = num;
  }
  fclose(file);

  MPI_Init(NULL, NULL);
  int world_size;
  MPI_Comm_size(MPI_COMM_WORLD, &world_size);
  int world_rank;
  MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

  mergeSortMPI(array, sizeOfArray, world_size, world_rank);

  MPI_Barrier(MPI_COMM_WORLD);
  if (world_rank == 0) {
    printf("\nEND\n");
  }
  MPI_Finalize();

  return 0;
}
