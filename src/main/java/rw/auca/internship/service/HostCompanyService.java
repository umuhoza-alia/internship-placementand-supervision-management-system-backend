package rw.auca.internship.service;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import org.springframework.stereotype.Service;
import rw.auca.internship.entity.HostCompany;
import rw.auca.internship.repository.HostCompanyRepository;

@Service
public class HostCompanyService {

    private final HostCompanyRepository hostCompanyRepository;
    public HostCompanyService(HostCompanyRepository hostCompanyRepository) {
        this.hostCompanyRepository = hostCompanyRepository;
    }

    public HostCompany saveHostCompany(HostCompany hostCompany) {
        return hostCompanyRepository.save(hostCompany);
    }
    public List<HostCompany> getAllHostCompanies() {
        return hostCompanyRepository.findAll();
    }
    public Optional<HostCompany> getHostCompanyById(UUID id) {
        return hostCompanyRepository.findById(id);
    }
    public Optional<HostCompany> updateHostCompany(UUID id, HostCompany updatedHostCompany) {
        return hostCompanyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setCompanyNumber(updatedHostCompany.getCompanyNumber());
                    existingCompany.setName(updatedHostCompany.getName());
                    return hostCompanyRepository.save(existingCompany);
                });
}

public boolean deleteHostCompany(UUID id) {
        if (hostCompanyRepository.existsById(id)) {
            hostCompanyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<HostCompany> getFreeHostCompanies() {
        return hostCompanyRepository.findFreeHostCompanies();
    }


}