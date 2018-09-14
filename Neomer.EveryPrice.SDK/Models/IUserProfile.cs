using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IUserProfile : IEntity
    {
        string Name { get; set; }

        DateTime? BirthDate { get; set; }

        IUser Owner { get; set; }
    }
}
