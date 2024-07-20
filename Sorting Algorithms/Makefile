#compiler setup
CXX = g++
MPICXX = mpic++
CXXFLAGS = -std=c++14 -O3 $(MACRO)
 
SERIAL= merge_sort_serial
PARALLEL= merge_sort_parallel
MPI= sort_mpi
ALL= $(SERIAL) $(PARALLEL) $(MPI)

all : $(ALL)

$(SERIAL): %: %.cpp
	$(CXX) $(CXXFLAGS) -o $@ $<

$(PARALLEL): %: %.cpp
	$(MPICXX) $(CXXFLAGS) -o $@ $<

$(MPI): %: %.cpp
	$(MPICXX) $(CXXFLAGS) -o $@ $<

.PHONY : clean

clean :
	rm -f *.o *.obj $(ALL)
