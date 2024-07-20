#include<stdlib.h>
#include<stdio.h>


typedef struct Data{
    int low;
    int high;
}Data;

const uint sizeOfArray = 20;
float arr[sizeOfArray];

void merge(int low, int mid, int high)
{
    int i, j, k;
    int num1 = mid - low + 1;
    int num2 =  high - mid;
    
    float left[num1], right[num2];
    for (i = 0; i < num1; i++)
        left[i] = arr[low + i];
    for (j = 0; j < num2; j++)
        right[j] = arr[mid+ 1+ j];
    
    i = 0;
    j = 0;
    k = low;
    while (i < num1 && j < num2)
    {
        if (left[i] <= right[j])
        {
            arr[k] = left[i];
            i++;
        }
        else
        {
            arr[k] = right[j];
            j++;
        }
        k++;
    }
    while (i < num1)
    {
        arr[k] = left[i];
        i++;
        k++;
    }
    
    while (j < num2)
    {
        arr[k] = right[j];
        j++;
        k++;
    }
}
void* mergeSort(Data* d)
{
  
    int low = d->low;
    int high = d->high;
    
    if (low < high)
    {
        int mid = low+(high-low)/2;
        
        Data d1 = {low, mid};
        mergeSort(&d1);
    
        Data d2 = {mid+1, high};
        mergeSort(&d2);
        
        merge(low, mid, high);
    }
}

int main()
{  
    FILE *file = NULL; 
    
    float floatArray[sizeOfArray], num ;
    int i = 0;
    file = fopen("Input.txt", "r");
    if( file == NULL ) {
        printf("Error while opening the file.\n");
        return -1;
    }
    while( fscanf(file, "%f", &num) != EOF ) {
        floatArray[i++] = num;
    }
    fclose(file);
    for( i = 0; i < sizeOfArray; i++ ){
        printf("floatArray[%d] = %.05f\n",i, floatArray[i]);
        arr[i] = floatArray[i];
    }
     
    Data data = {0, sizeOfArray};
    mergeSort(&data);
    for( i = 0; i < sizeOfArray; i++ ){
        printf("floatArray[%d] = %.05f\n",i, arr[i]);
    }

    return 0;
}