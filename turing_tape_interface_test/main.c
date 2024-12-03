#include <stdio.h>

double _numbers[10000] = { 0 };

int main ( int argc, const char *argv[] )
{
    
    size_t i = 0;

    while(!feof(stdin))
    {
        scanf(" %lf ", &_numbers[i]);
        i++;
    }

    // Avg
    {
        double avg = 0;

        for (size_t j = 0; j < i; j++)
            avg += _numbers[j];
        
        printf("Average: %.6lf\n", avg / (double)i);
    }

    // Median
    printf("Median : %.6lf\n", _numbers[i/2]);

    return 0;
}
